package com.lagou.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * 依赖注入注解
 * @author ying
 * @version 1.0
 * @date 2021-04-22 01:01
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LagouAutowired {

        String value() default "";
}
