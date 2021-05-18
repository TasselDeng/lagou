package com.lagou.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ying
 * @version 1.0
 * @date 2021-05-10 13:31
 */
@RestController
public class DemoController {

    @RequestMapping("/demo01")
    public String demo01() {
        return "demo热部署";
    }

}
