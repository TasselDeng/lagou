package com.lagou.service;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI("human") // 指定一个字符串参数，用于指明该SPI的默认实现。
public interface HelloService {
    String  sayHello();
    @Adaptive
    String  sayHello(URL  url);
}
