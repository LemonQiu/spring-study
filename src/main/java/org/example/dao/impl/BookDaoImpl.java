package org.example.dao.impl;

import org.example.bean.Book;
import org.example.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Author qiu
 * @Date 2021/1/9 23:02
 */
@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Book getBook(String bookName) {
        String sql = "select id, price, book_name as bookName from book where book_name = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Book.class), bookName);
    }

    @Override
    public void subPrice(Integer id, Double price) {
        String sql = "update book set price = price - ? where id = ?";
        jdbcTemplate.update(sql, price, id);
    }

    @Override
    public void addPrice(Integer id, Double price) {
        String sql = "update book set price = price + ? where id = ?";
        jdbcTemplate.update(sql, price, id);
    }
}
