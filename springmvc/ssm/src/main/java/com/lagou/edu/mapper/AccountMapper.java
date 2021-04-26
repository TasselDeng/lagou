package com.lagou.edu.mapper;

import com.lagou.edu.pojo.Account;

import java.util.List;

/**
 * @author ying
 * @version 1.0
 * @date 2021-04-26 23:54
 */
public interface AccountMapper {

    List<Account> queryAll();
}
