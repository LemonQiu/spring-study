package org.example.service.impl;

import org.example.bean.Book;
import org.example.dao.BookDao;
import org.example.dao.BookStockDao;
import org.example.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author qiu
 * @Date 2021/1/9 23:31
 */
@Service
public class BookServiceImpl implements BookService {

    @Resource
    private BookDao bookDao;

    @Resource
    private BookStockDao bookStockDao;


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void collectionAmount(String bookName) {
        Book book = bookDao.getBook(bookName);
        if(book == null) {
            throw new RuntimeException("book is null and arg is " + bookName);
        }

        bookStockDao.subStock(book.getId(), book.getPrice());
    }

    @Override
    public Double getPrice(String bookName) {
        Book book = bookDao.getBook(bookName);
        if(book == null) {
            throw new RuntimeException("book is null and arg is " + bookName);
        }
        return book.getPrice();
    }
}
