package com.lagou.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author ying
 * @version 1.0
 * @date 2021-05-10 23:21
 */
@Data
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

    private String name;

    private List<String> hobby;

    private String[] family;

    private Map<String, Object> map;

    private Pet pet;
}
