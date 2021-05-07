package com.lagou.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * 访问权限注解
 * @author ying
 * @version 1.0
 * @date 2021-05-07 21:01
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Security {

        String[] value() default {};
}
