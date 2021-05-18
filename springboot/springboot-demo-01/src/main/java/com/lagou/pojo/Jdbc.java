package com.lagou.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ying
 * @version 1.0
 * @date 2021-05-14 00:48
 */
@Configuration
@PropertySource(value = {"classpath:/jdbcmsg.properties"})
@Data
public class Jdbc {

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;
    
}
