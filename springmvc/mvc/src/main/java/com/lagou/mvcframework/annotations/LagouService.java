package com.lagou.mvcframework.annotations;

import java.lang.annotation.*;

/**
 * 服务层注解
 * @author ying
 * @version 1.0
 * @date 2021-04-22 01:01
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LagouService {

        String value() default "";
}
