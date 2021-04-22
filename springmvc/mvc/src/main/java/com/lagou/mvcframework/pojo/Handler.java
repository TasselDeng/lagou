package com.lagou.mvcframework.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author ying
 * @version 1.0
 * @date 2021-04-22 22:13
 */
@Getter
@Setter
public class Handler {

    /**
     * controller对象
     */
    private Object controller;

    /**
     * controller方法
     */
    private Method method;

    /**
     * url
     */
    private Pattern pattern;

    /**
     * 参数名和顺序
     */
    private Map<String, Integer> paramIndexMapping;

    public Handler(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
        this.paramIndexMapping = new HashMap<>();
    }
}
