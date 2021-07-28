package com.lagou.sbrd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author y-time
 * @version 1.0
 * @date 2021-07-22 12:28
 */
@RestController
@RequestMapping(value = "/redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/put")
    public String put(@RequestParam String key, @RequestParam String value) {
        // 设置过期时间为20秒
        redisTemplate.opsForValue().set(key, value, 20, TimeUnit.SECONDS);
        return "Success";
    }

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
}
