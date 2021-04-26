package com.lagou.edu.controller;

import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ying
 * @version 1.0
 * @date 2021-04-26 23:46
 */
@Controller
@RequestMapping("/demo")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping("/queryAccount")
    @ResponseBody
    public List<Account> queryAll() {
        return accountService.queruAll();
    }

}
