package com.lagou.demo.controller;

import com.lagou.demo.service.IDemoService;
import com.lagou.mvcframework.annotations.LagouAutowired;
import com.lagou.mvcframework.annotations.LagouController;
import com.lagou.mvcframework.annotations.LagouRequestMapping;
import com.lagou.mvcframework.annotations.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 控制层
 *
 * @author ying
 * @version 1.0
 * @date 2021-04-22 01:17
 */
@Security(value = {"wangwu"})
@LagouController
@LagouRequestMapping("/demo")
public class DemoController {

    @LagouAutowired
    private IDemoService demoService;

    @LagouRequestMapping("/getName")
    public String getName(HttpServletRequest request, HttpServletResponse response, String username) {
        return demoService.getName(username);
    }

    @Security(value = {"zhangsan"})
    @LagouRequestMapping("/getName01")
    public String getName01(HttpServletRequest request, HttpServletResponse response, String username) throws IOException {
        String name = demoService.getName(username);
        response.getWriter().write(name);
        return name;
    }

    @Security(value = {"zhangsan", "lisi"})
    @LagouRequestMapping("/getName02")
    public String getName02(HttpServletRequest request, HttpServletResponse response, String username) throws IOException {
        String name = demoService.getName(username);
        response.getWriter().write(name);
        return name;
    }

    @LagouRequestMapping("/getName03")
    public String getName03(HttpServletRequest request, HttpServletResponse response, String username) throws IOException {
        String name = demoService.getName(username);
        response.getWriter().write(name);
        return name;
    }
}
