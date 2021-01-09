package org.example.service.impl;

import org.example.service.AccountService;
import org.example.service.BookService;
import org.example.service.BookStoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author qiu
 * @Date 2021/1/10 0:30
 */
@Service
public class BookStoreServiceImpl implements BookStoreService {

    @Resource
    private BookService bookService;

    @Resource
    private AccountService accountService;

    /**
     * @Transactional：使用注解的方式为声明式注解
     * propagation：事务传播行为，为了解决方法调用之间，如果有多个事务，事务之间如何处理？
     *      REQUIRED：如果外部有事务，则使用外部事务，否则创建一个新的事务，运行在新的事务中
     *          因为已经有外部事务了，因为运行在同一个事务内，所以会一起回滚。
     *          如果当前方法报错，即使被外部方法捕获，外部事务也会进行回滚。
     *
     *      REQUIRED_NEW：如果外部有事务，则将事务挂起，然后创建一个新的事务，当前方法运行在新的事务中
     *          自己的事务正常提交，则不会因为外部事务报错而回滚。
     *          如果当前方法报错，但被外部方法捕获，则外部事务不会进行回滚。
     *
     *      SUPPORTS：如果外部有事务，则运行在外部事务中，否则不运行事务。
     *          因为已经有外部事务了，那么外部事务报错，则会直接回滚。否则因为没有开启声明式事务，则走的是mysql的事务
     *
     *      NOT_SUPPORTED：当前方法不运行事务，如果有事务，则将它挂起。
     *          即使外部开启了事务，当时直接将外部事务挂起，自己不在事务内执行，走mysql的事务，所以外部事务报错，当前方法不会进行回滚
     *
     *      MANDATORY：当前方法必须运行在事务中，如果没有事务，则抛出异常。
     *          只能运行在外部事务内，所以外部方法报错，会跟随着一起回滚
     *
     *      NEVER：当前方法必须不运行在事务中，如果有事务，则抛出异常。
     *          因为没有事务，所以走的是mysql的事务管理
     *
     *      NESTED：如果外部有事务，则在外部事务的嵌套事务中运行，否则创建一个新的事务，并运行在新的事务内
     *          PROPAGATION_NESTED开始一个 "嵌套的" 事务,  它是已经存在事务的一个真正的子事务. 嵌套事务开始执行时,  它将取得一个 savepoint.
     *          如果这个嵌套事务失败, 我们将回滚到此 savepoint. 潜套事务是外部事务的一部分, 只有外部事务结束后它才会被提交.
     *          PROPAGATION_REQUIRES_NEW 完全是一个新的事务, 而 PROPAGATION_NESTED则是外部事务的子事务, 如果外部事务commit, 嵌套事务也会被commit, 这个规则同样适用于roll back。
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void buyBook(String username, String bookName) {
        Double price = bookService.getPrice(bookName);
        try{
            accountService.payAmount(username, price);
        } catch (Exception e) {
        }
        bookService.collectionAmount(bookName);
    }
}
