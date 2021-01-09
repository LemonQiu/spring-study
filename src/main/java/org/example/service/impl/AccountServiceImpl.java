package org.example.service.impl;

import org.example.bean.Account;
import org.example.dao.AccountDao;
import org.example.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author qiu
 * @Date 2021/1/10 0:19
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void payAmount(String username, Double balance) {
        Account account = accountDao.getAccount(username);
        if(account == null) {
            throw new RuntimeException("account is null and arg is " + username);
        }

        accountDao.subBalance(username, balance);
        int i = 1 / 0;
    }
}
