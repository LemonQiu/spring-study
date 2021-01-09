package org.example.dao.impl;

import org.example.dao.BookStockDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @Author qiu
 * @Date 2021/1/9 23:48
 */
@Repository
public class BookStockDaoImpl implements BookStockDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void subStock(int id, double stock) {
        String sql = "update book_stock set stock = stock - ? where id = ?";
        jdbcTemplate.update(sql, stock, id);
    }
}
