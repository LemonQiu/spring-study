package org.example.dao;

import org.example.bean.Book;

/**
 * @Author qiu
 * @Date 2021/1/9 23:00
 */
public interface BookDao {

    Book getBook(String bookName);

    void subPrice(Integer id, Double price);

    void addPrice(Integer id, Double price);
}
