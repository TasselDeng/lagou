package com.lagou.mapper;

import com.lagou.pojo.User;

import java.util.List;

/**
 * User xml对应的查询接口
 *
 * @author ying
 * @version 1.0
 * @date 2021-01-26 23:45
 */
public interface IUserDao {

    List<User> findAll();

    User findByCondition(User user);
}
