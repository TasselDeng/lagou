package com.lagou.mvcframework.servlet;

import com.lagou.mvcframework.annotations.LagouAutowired;
import com.lagou.mvcframework.annotations.LagouController;
import com.lagou.mvcframework.annotations.LagouRequestMapping;
import com.lagou.mvcframework.annotations.LagouService;
import com.lagou.mvcframework.pojo.Handler;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 统一 dispatherServlet
 *
 * @author ying
 * @version 1.0
 * @date 2021-04-22 01:09
 */
public class LagouDispatherServlet extends HttpServlet {

    /**
     * springmcv配置文件信息
     */
    private Properties properties = new Properties();

    /**
     * 扫描到的全限定类名
     */
    private List<String> classNameList = new ArrayList<>();

    /**
     * ioc容器
     */
    private Map<String, Object> iocMap = new HashMap<>();

    /**
     * controller信息list，url、Method、对象、参数
     */
    private List<Handler> handlerList = new ArrayList<>();

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // 1、加载配置文件springmvc.properties，获取扫描包路径等信息
        doLoadConfig(servletConfig);

        // 2、包扫描，获取所有类全限定类名存入classNameList
        doScan(properties.getProperty("scanPackage"));

        // 3、初始化bean对象
        doInstance();

        // 4、实现依赖注入
        doAutoWired();

        // 5、构造一个HandlerMappering处理器映射器，将配置好的url和method建立映射关系
        initHandlerMapping();
    }

    /**
     * HandlerMappering处理器映射器
     */
    private void initHandlerMapping() {
        // 遍历ioc容器中带@LagouController注解的bean，封装handler，存入handlerList
        if (iocMap.isEmpty()) {
            return;
        }
        Set<Map.Entry<String, Object>> entrySet = iocMap.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            Object o = entry.getValue();
            Class<?> aClass = o.getClass();
            if (!aClass.isAnnotationPresent(LagouController.class)) {
                continue;
            }
            LagouRequestMapping lagouRequestMapping = aClass.getAnnotation(LagouRequestMapping.class);
            // 类上的url
            String typeUrl = lagouRequestMapping.value();
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(LagouRequestMapping.class)) {
                    continue;
                }
                LagouRequestMapping methodLagouRequestMapping = method.getAnnotation(LagouRequestMapping.class);
                String methodUrl = methodLagouRequestMapping.value();
                // 完整url
                String url = typeUrl + methodUrl;
                // 封装handler
                Handler handler = new Handler(o, method, Pattern.compile(url));
                // 参数信息
                Map<String, Integer> paramIndexMapping = handler.getParamIndexMapping();
                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    Class<?> parameterType = parameter.getType();
                    if (parameterType == HttpServletRequest.class || parameterType == HttpServletResponse.class) {
                        // 如果是request和response对象，那么参数名称写HttpServletRequest和HttpServletResponse
                        paramIndexMapping.put(parameterType.getSimpleName(), i);
                    } else {
                        paramIndexMapping.put(parameter.getName(), i);
                    }
                }
                handlerList.add(handler);
            }
        }
    }

    /**
     * 遍历ioc容器的bean，带@LagouAutowired的属性，注入对象
     */
    private void doAutoWired() {
        if (iocMap.isEmpty()) {
            return;
        }
        Set<Map.Entry<String, Object>> entrySet = iocMap.entrySet();
        // 遍历ioc容器每个bean的每个属性，对有@LagouAutowired注解的属性进行依赖注入
        for (Map.Entry<String, Object> entry : entrySet) {
            Object o = entry.getValue();
            Field[] declaredFields = o.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (!declaredField.isAnnotationPresent(LagouAutowired.class)) {
                    continue;
                }
                LagouAutowired lagouAutowired = declaredField.getAnnotation(LagouAutowired.class);
                // 有value值的用value找bean，没有用首字母小写类名找
                String value = lagouAutowired.value();
                String iocKey = StringUtils.isEmpty(value) ? declaredField.getType().getName() : value;
                Object iocValue = iocMap.get(iocKey);
                // 注入bean
                declaredField.setAccessible(true);
                try {
                    declaredField.set(o, iocValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 实例化带@LagouController、@LagouService的bean，存入ioc容器中
     */
    private void doInstance() {
        if (CollectionUtils.isEmpty(classNameList)) {
            return;
        }
        try {
            for (String className : classNameList) {
                Class<?> aClass = Class.forName(className);
                // @LagouController注解的类直接用类名首字母小写作为key
                if (aClass.isAnnotationPresent(LagouController.class)) {
                    String simpleName = aClass.getSimpleName();
                    String aClassSimpleName = lowerFirst(simpleName);
                    iocMap.put(aClassSimpleName, aClass.getDeclaredConstructor().newInstance());
                    // @LagouService先获取value值，有value值使用value作为key，没有用类名首字母小写作为key
                } else if (aClass.isAnnotationPresent(LagouService.class)) {
                    LagouService lagouService = aClass.getAnnotation(LagouService.class);
                    String value = lagouService.value();
                    Object o = aClass.getDeclaredConstructor().newInstance();
                    if (StringUtils.isEmpty(value)) {
                        iocMap.put(lowerFirst(aClass.getSimpleName()), o);
                    } else {
                        iocMap.put(value, o);
                    }
                    // service层往往是有接口的，面向接口开发，此时再以接口名为id，放入一份对象到ioc中，便于后期根据接口类型注入
                    Class<?>[] interfaces = aClass.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        iocMap.put(anInterface.getName(), o);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 将首字母设置成小写
     *
     * @param simpleName 类名
     * @return
     */
    private String lowerFirst(String simpleName) {
        char[] chars = simpleName.toCharArray();
        if (chars[0] >= 'A' && chars[0] <= 'Z') {
            chars[0] += 32;
        }
        return String.valueOf(chars);
    }

    /**
     * 递归扫描包下所有类，获取所有类的全限定类名
     *
     * @param scanPackage 包相对路径
     */
    private void doScan(String scanPackage) {
        // 获取包在当前ClassPath的绝对URI路径
        String scanPackagePath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                + scanPackage.replaceAll("\\.", "/");
        // 获取包下所有文件，是目录就递归向下扫描，是类文件就将类全限定类名存入list
        File packageFile = new File(scanPackagePath);
        File[] packageFiles = packageFile.listFiles();
        for (File file : packageFiles) {
            if (file.isDirectory()) {
                doScan(scanPackage + "." + file.getName());
            } else {
                String className = scanPackage + "." + file.getName().replaceAll(".class", "");
                classNameList.add(className);
            }
        }
    }

    /**
     * 加载配置文件
     *
     * @param servletConfig
     */
    private void doLoadConfig(ServletConfig servletConfig) {
        // web.xml配置的配置文件名
        String contextConfigLocation = servletConfig.getInitParameter("contextConfigLocation");
        // 通过类加载器获取资源文件流
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (CollectionUtils.isEmpty(handlerList)) {
            resp.getWriter().write("404 not found");
            return;
        }
        // 更具url去除handler
        String url = req.getRequestURI();
        Handler handler = handlerList.stream()
                .filter(e -> e.getPattern().matcher(url).matches())
                .findFirst().orElse(null);
        if (handler == null) {
            resp.getWriter().write("404 not found");
            return;
        }
        // 参数绑定
        Parameter[] parameters = handler.getMethod().getParameters();
        Object[] paramValues = new Object[parameters.length];
        // 遍历request中的参数，填充出request和response外的参数
        Map<String, String[]> parameterMap = req.getParameterMap();
        Map<String, Integer> paramIndexMapping = handler.getParamIndexMapping();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            if (!paramIndexMapping.containsKey(key)) {
                continue;
            }
            Integer index = paramIndexMapping.get(key);
            String value = StringUtils.join(entry.getValue(), ",");
            paramValues[index] = value;
        }
        // request和response
        int requestIndex = paramIndexMapping.get(HttpServletRequest.class.getSimpleName());
        paramValues[requestIndex] = req;

        int responseIndex = paramIndexMapping.get(HttpServletResponse.class.getSimpleName());
        paramValues[responseIndex] = resp;

        // 最终调用handler的method属性
        try {
            handler.getMethod().invoke(handler.getController(), paramValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
