package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.mvcframework.annotations.LagouAutowired;
import com.lagou.mvcframework.annotations.LagouController;
import com.lagou.mvcframework.annotations.LagouRequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制层
 *
 * @author ying
 * @version 1.0
 * @date 2021-04-22 01:17
 */
@LagouController
@LagouRequestMapping("/demo")
public class DemoController {

    @LagouAutowired
    private IDemoService demoService;

    @LagouRequestMapping("/getName")
    public String getName(HttpServletRequest request, HttpServletResponse response, String name) {
        return demoService.getName(name);
    }
}
