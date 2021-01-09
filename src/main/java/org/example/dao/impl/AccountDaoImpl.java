package org.example.dao.impl;

import org.example.bean.Account;
import org.example.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * @Author qiu
 * @Date 2021/1/9 23:22
 */
@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Account getAccount(String username) {
        if(!StringUtils.hasText(username)) {
            return null;
        }

        String sql = "select username, balance from account where username = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Account.class), username);
    }

    @Override
    public void subBalance(String username, Double balance) {
        String sql = "update account set balance = balance - ? where username = ?";
        jdbcTemplate.update(sql, balance, username);
    }
}
