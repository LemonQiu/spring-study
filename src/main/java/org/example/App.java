package org.example;

import org.example.service.BookStoreService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 */
public class App {

    private static final ClassPathXmlApplicationContext CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");

    public static void main(String[] args) {
        BookStoreService bookStoreService = CONTEXT.getBean(BookStoreService.class);
        bookStoreService.buyBook("lisi", "西游记");
    }
}
