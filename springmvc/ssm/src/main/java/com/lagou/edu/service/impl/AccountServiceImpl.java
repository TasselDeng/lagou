package com.lagou.edu.service.impl;

import com.lagou.edu.mapper.AccountMapper;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ying
 * @version 1.0
 * @date 2021-04-26 23:52
 */
@Service
@Transactional
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Account> queruAll() {
        return accountMapper.queryAll();
    }
}
