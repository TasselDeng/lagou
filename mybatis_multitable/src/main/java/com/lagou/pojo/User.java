package com.lagou.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @author ying
 * @version 1.0
 * @date 2021-01-23 18:51
 */
@Data
public class User {

    private Integer id;

    private String username;

    private String password;

    private Date birthday;

    private List<Order> orderList;

    private List<Role> roleList;
}
