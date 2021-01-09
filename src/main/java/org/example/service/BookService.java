package org.example.service;

/**
 * @Author qiu
 * @Date 2021/1/9 23:10
 */
public interface BookService {

    void collectionAmount(String bookName);

    Double getPrice(String bookName);
}
