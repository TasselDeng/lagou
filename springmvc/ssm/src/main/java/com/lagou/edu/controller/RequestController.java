package com.lagou.edu.controller;

import com.lagou.edu.pojo.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author y-time
 * @version 1.0
 * @date 2021-07-12 16:52
 */
@Controller
@RequestMapping("/request")
public class RequestController {

    @GetMapping("/get")
    @ResponseBody
    public String getTest(HttpServletRequest request) {
        return "get:" + request.getParameter("username");
    }

    @PostMapping("/post")
    @ResponseBody
    public String postTest(HttpServletRequest request) {
        return "post:" + request.getParameter("username");
    }

}
