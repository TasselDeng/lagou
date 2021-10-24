package com.lagou.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author ying
 * @date 2021-10-18 23:34
 **/
@Component
public class ConsumerComponent {

    @Reference
    private HelloService helloService;

    public String sayHello(String name) {
        return helloService.sayHello(name);
    }
}
