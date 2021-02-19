package com.lagou.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class User {

    private Integer id;

    private String username;

    private String password;

    private Date birthday;

    private List<Order> orderList;

    private List<Role> roleList;

    public User(Integer id, String username, String password, Date birthday) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
    }
}
