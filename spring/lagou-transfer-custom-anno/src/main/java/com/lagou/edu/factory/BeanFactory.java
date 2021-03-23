package com.lagou.edu.factory;

import com.lagou.edu.annotation.Autowired;
import com.lagou.edu.annotation.Service;
import com.lagou.edu.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 工厂类，生产对象（使用反射技术）
 *
 * @author ying
 * @version 1.0
 * @date 2021-03-23 23:20
 */
public class BeanFactory {

    /**
     * 任务一：扫描bao，通过反射技术实例化对象并且存储待用（map集合）
     * 任务二：对外提供获取实例对象的接口（根据id获取）
     */

    private static Map<String, Object> map = new HashMap<>();  // 存储对象


    static {
        try {
            // 扫描包
            Reflections reflections = new Reflections("com.lagou.edu");
            // 获取注解为Service的类
            Set<Class<?>> serviceClassSet = reflections.getTypesAnnotatedWith(Service.class);
            // 实例化注解为Service的类，放入map中
            for (Class<?> serviceClass : serviceClassSet) {
                Object serviceBean = serviceClass.getDeclaredConstructor().newInstance();
                Service serviceAnnotation = serviceClass.getAnnotation(Service.class);
                // 如果Service注解有value，则将value值作为bean的key，否则用类名
                String serviceAnnotationValue = serviceAnnotation.value();
                if (StringUtils.isEmpty(serviceAnnotationValue)) {
                    String serviceClassName = serviceClass.getName();
                    String[] split = serviceClassName.split("\\.");
                    map.put(split[split.length - 1], serviceBean);
                } else {
                    map.put(serviceAnnotationValue, serviceBean);
                }
            }
            // 遍历map，检查有注解Autowired属性的类，注入对应依赖的值，是否有Transactional注解，若有则修改对象为代理对象
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object bean = entry.getValue();
                Class<?> beanClass = bean.getClass();
                Field[] fields = beanClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
                        String autowiredValue = autowiredAnnotation.value();
                        Object fieldValue;
                        // 用value值的Autowired注解用value值去找对应bean，否则用类型去找
                        if (StringUtils.isEmpty(autowiredValue)) {
                            Class<?> fieldType = field.getType();
                            String fieldTypeName = fieldType.getName();
                            String[] split = fieldTypeName.split("\\.");
                            fieldValue = map.get(split[split.length - 1]);
                        } else {
                            fieldValue = map.get(autowiredValue);
                        }
                        // 属性注入值
                        field.setAccessible(true);
                        field.set(bean, fieldValue);
                    }
                }

                if (beanClass.isAnnotationPresent(Transactional.class)) {
                    // 获取代理工厂
                    ProxyFactory proxyFactory = (ProxyFactory) BeanFactory.getBean("ProxyFactory");
                    // 是否实现接口
                    Class<?>[] interfaces = beanClass.getInterfaces();
                    if (interfaces != null && interfaces.length > 0) {
                        bean = proxyFactory.getJdkProxy(bean);
                    } else {
                        bean = proxyFactory.getCglibProxy(bean);
                    }
                    map.put(entry.getKey(), bean);
                }
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    // 任务二：对外提供获取实例对象的接口（根据id获取）
    public static Object getBean(String id) {
        return map.get(id);
    }

}
