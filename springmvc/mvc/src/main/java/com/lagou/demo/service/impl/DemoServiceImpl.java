package com.lagou.demo.service.impl;

import com.lagou.demo.service.IDemoService;
import com.lagou.mvcframework.annotations.LagouService;

/**
 * 服务层
 *
 * @author ying
 * @version 1.0
 * @date 2021-04-22 01:18
 */
@LagouService
public class DemoServiceImpl implements IDemoService {

    @Override
    public String getName(String name) {
        System.out.println(name);
        return "service:" + name;
    }
}
