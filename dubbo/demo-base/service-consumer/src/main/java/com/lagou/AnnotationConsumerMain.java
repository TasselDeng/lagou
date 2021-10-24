package com.lagou;

import com.lagou.service.ConsumerComponent;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

/**
 * TODO
 *
 * @author ying
 * @date 2021-10-18 23:41
 **/
public class AnnotationConsumerMain {
    @Configuration
    @EnableDubbo(scanBasePackages = "com.lagou.service")
    @PropertySource("classpath:/dubbo-consumer.properties")
    @ComponentScan(value = {"com.lagou.service"})
    static class ConsumerConfiguration {
    }

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        ConsumerComponent service = context.getBean(ConsumerComponent.class);
        while (true) {
            System.in.read();
            try {
                String hello = service.sayHello("world");
                System.out.println("result :" + hello);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
