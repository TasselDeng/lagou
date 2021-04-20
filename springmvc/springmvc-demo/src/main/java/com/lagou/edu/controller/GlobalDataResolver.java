package com.lagou.edu.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局数据绑定
 *
 * @author ying
 * @version 1.0
 * @date 2021-04-18 22:52
 */
@ControllerAdvice
public class GlobalDataResolver {

    @ModelAttribute(value = "info")
    public Map<String, Object> getInfo() {
        Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("author", "ying");
        infoMap.put("version", "1.0");
        return infoMap;
    }
}
