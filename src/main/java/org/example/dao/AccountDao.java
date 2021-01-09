package org.example.dao;

import org.example.bean.Account;

/**
 * @Author qiu
 * @Date 2021/1/9 23:21
 */
public interface AccountDao {

    Account getAccount(String username);

    void subBalance(String username, Double balance);
}
