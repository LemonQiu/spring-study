package org.example;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.example.bean.Person;
import org.example.bean.Person2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * 默认情况下，对象在容器初始化完成的时候就已经创建了，而不是在使用的时候才创建
     * 在执行完下面的代码时，已经输出了Person类的无参构造方法中的内容：Person is create
     */
    @Test
    public void testObjectCreate() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-1.xml");
    }

    /**
     * 可以通过指定的id + type从获取对象
     * 默认情况下是单例的，可以通过scope进行指定
     * person1 == person2 true
     */
    @Test
    public void testIoc() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-1.xml");
        Person person1 = applicationContext.getBean("person", Person.class);
        Person person2 = applicationContext.getBean("person", Person.class);
        System.out.println(person1);
        System.out.println(person2);
        System.out.println(person1 == person2);
    }

    /**
     * 也可以只使用type来获取对象
     * 但是如果该type有多个配置，则会报错
     * NoUniqueBeanDefinitionException
     */
    @Test
    public void testType() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-2.xml");
        Person person = applicationContext.getBean(Person.class);
        System.out.println(person);
    }

    /**
     * 使用有参构造进行赋值的多种方式
     */
    @Test
    public void testConstr() {
        // 使用有参构造的方式进行赋值与创建-->使用name属性
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-3.xml");
        Person person2 = applicationContext.getBean("person2", Person.class);
        System.out.println(person2);

        // 使用有参构造的方式进行赋值与创建-->忽略name属性，但要保证顺序
        System.out.println("----------------------------------------------------------------");
        Person person3 = applicationContext.getBean("person3", Person.class);
        System.out.println(person3);

        // 使用有参构造的方式进行赋值与创建-->忽略name属性，不保证属性，可以通过index指定
        System.out.println("----------------------------------------------------------------");
        Person person4 = applicationContext.getBean("person4", Person.class);
        System.out.println(person4);
    }

    /**
     * 测试复杂属性的赋值
     */
    @Test
    public void testComplexField() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-4.xml");
        Person2 person = applicationContext.getBean("person", Person2.class);
        System.out.println(person);
    }

    /**
     * 测试复杂属性的赋值
     *         默认情况下，按照xml中定义bean的前后来顺序创建对象的
     *         但是可以通过depends-on属性来控制顺序
     *         同时也可以通过abstract来表示该类是一个抽象类，无法实例化
     */
    @Test
    public void testOrder() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-5.xml");
    }

    /**
     * 可以指定bean的作用域
     *    默认  singleton：单例模式，IOC永远返回同一个对象，在IOC创建完成之前就已经创建好了
     *          prototype：多例模式，每次都会创建一个对象返回，每次用的时候才会创建
     *          request：每有一个请求创建一个对象
     *          session：没有一个会话创建一个对象
     */
    @Test
    public void testPrototype() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-6.xml");
        Person person1 = applicationContext.getBean("person", Person.class);
        Person person2 = applicationContext.getBean("person", Person.class);
        System.out.println(person1 == person2);
    }

    /**
     * 通过静态工厂和实例工厂的方式来创建bean
     */
    @Test
    public void testFactory() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-7.xml");
        Person person1 = applicationContext.getBean("person", Person.class);
        Person person2 = applicationContext.getBean("person2", Person.class);
        System.out.println(person1);
        System.out.println(person2);
    }

    /**
     * 通过FactoryBean的实现类来创建对象
     */
    @Test
    public void testFactoryBean() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-8.xml");
        Person person = applicationContext.getBean("personFactoryBean", Person.class);
        System.out.println(person);
    }

    /**
     * bean的初始化方法和销毁方法
     */
    @Test
    public void testBeanInitAndDestroy() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-9.xml");
        Person person = applicationContext.getBean("person", Person.class);
        System.out.println(person);
        // 只有在关闭了容器的时候，才会调用destroy方法
        // 但是如果bean的scope域是prototype，则不会调用
        applicationContext.close();
    }

    /**
     * 前置处理器和后置处理器
     */
    @Test
    public void testBeanPostProcessor() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-10.xml");
        Person person = applicationContext.getBean("person", Person.class);
        System.out.println(person);
        // 只有在关闭了容器的时候，才会调用destroy方法
        // 但是如果bean的scope域是prototype，则不会调用
        applicationContext.close();
    }

    /**
     * spring管理第三方bean
     */
    @Test
    public void testOtherBean() throws SQLException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-11.xml");
        DruidDataSource druidDataSource = applicationContext.getBean("dataSource", DruidDataSource.class);
        DruidPooledConnection connection = druidDataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

    /**
     * spring管理第三方bean
     * 可以使用配置的方式管理数据源
     */
    @Test
    public void testOtherBean2() throws SQLException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-12.xml");
        DruidDataSource druidDataSource = applicationContext.getBean("dataSource", DruidDataSource.class);
        DruidPooledConnection connection = druidDataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

    /**
     *  使用自动装配来自动导入bean的依赖
     *     default/no：不使用自动装配
     *     byName：根据名称来进行自动装配，需要setter方法的名称与依赖的bean的id一致才可以
     *     byType：根据类型来进行自动装配，如果有多个，则会报错
     *     constructor：需要只含有依赖bean的有参构造，先根据类型自动装配，如果有多个，则根据名称进行自动装配
     */
    @Test
    public void testAutowire() throws SQLException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-13.xml");
        Person2 person1 = applicationContext.getBean("person1", Person2.class);
        System.out.println("default: " + person1);
        System.out.println("-----------------------------------------------------------");

        Person2 person2 = applicationContext.getBean("person2", Person2.class);
        System.out.println("no: " + person2);
        System.out.println("-----------------------------------------------------------");

        Person2 person3 = applicationContext.getBean("person3", Person2.class);
        System.out.println("byName: " + person3);
        System.out.println("-----------------------------------------------------------");

        Person2 person4 = applicationContext.getBean("person4", Person2.class);
        System.out.println("byType: " + person4);
        System.out.println("-----------------------------------------------------------");

        Person2 person5 = applicationContext.getBean("person5", Person2.class);
        System.out.println("constructor: " + person5);
        System.out.println("-----------------------------------------------------------");
    }

}
