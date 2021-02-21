package com.lagou.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * mybatis自定义插件
 *
 * @author ying
 * @version 1.0
 * @date 2021-02-21 22:16
 */
// mybatis拦截器注解
@Intercepts({
        // 要拦截的四大对象的哪个方法，因为方法有重载，需要通过参数确定哪个方法
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
})
public class MyPlugin implements Interceptor {

    // 增强逻辑
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("自定义插件增强逻辑");
        // 调用原方法
        return invocation.proceed();
    }

    // 把这个拦截器⽣成⼀个代理放到拦截器链
    @Override
    public Object plugin(Object target) {
        System.out.println("将要包装的⽬标对象： " + target);
        return Plugin.wrap(target, this);
    }

    // 获取配置⽂件的属性，插件初始化的时候调⽤，也只调⽤⼀次，插件配置的属性从这⾥设置进来
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置的初始化参数： " + properties);
    }
}
