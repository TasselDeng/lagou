package com.lagou.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * URL映射注解
 * @author ying
 * @version 1.0
 * @date 2021-04-22 01:01
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LagouRequestMapping {

        String value() default "";
}
