package org.example;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.example.bean.Person;
import org.example.bean.Person2;
import org.example.proxy.ProxyFactory;
import org.example.service.CalculateService;
import org.example.service.impl.CalculateServiceImpl;
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
        System.out.println("----------------------------------------------------------------\n");
        Person person3 = applicationContext.getBean("person3", Person.class);
        System.out.println(person3);

        // 使用有参构造的方式进行赋值与创建-->忽略name属性，不保证属性，可以通过index指定
        System.out.println("----------------------------------------------------------------\n");
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
        System.out.println("-----------------------------------------------------------\n");

        Person2 person2 = applicationContext.getBean("person2", Person2.class);
        System.out.println("no: " + person2);
        System.out.println("-----------------------------------------------------------\n");

        Person2 person3 = applicationContext.getBean("person3", Person2.class);
        System.out.println("byName: " + person3);
        System.out.println("-----------------------------------------------------------\n");

        Person2 person4 = applicationContext.getBean("person4", Person2.class);
        System.out.println("byType: " + person4);
        System.out.println("-----------------------------------------------------------\n");

        Person2 person5 = applicationContext.getBean("person5", Person2.class);
        System.out.println("constructor: " + person5);
        System.out.println("-----------------------------------------------------------\n");
    }

    /**
     * 测试SpEL表达式
     */
    @Test
    public void testSpEL() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc-14.xml");
        Person2 person = applicationContext.getBean("person", Person2.class);
        System.out.println(person);
    }

    /**
     * 测试JDK的动态代理
     */
    @Test
    public void testJDKProxy() {
        CalculateService calculateService = ProxyFactory.getJDKProxy(new CalculateServiceImpl());
        calculateService.add(1,1);
        System.out.println("-----------------------------------\n");
        calculateService.sub(1,1);
        System.out.println("-----------------------------------\n");
        calculateService.mul(1,1);
        System.out.println("-----------------------------------\n");
        calculateService.div(1,0);
    }

    /**
     * 测试CGLib的动态代理
     */
    @Test
    public void testCgLibProxy() {
        CalculateService calculateService = ProxyFactory.getCgLibProxy(new CalculateServiceImpl());
        calculateService.add(1,1);
        System.out.println("-----------------------------------\n");
        calculateService.sub(1,1);
        System.out.println("-----------------------------------\n");
        calculateService.mul(1,1);
        System.out.println("-----------------------------------\n");
        calculateService.div(1,0);
    }

    /**
     * 测试Aspectj 注解版
     *  切面：代理类
     *  连接点：可以被代理的方法
     *  切入点：真正被代理的方法
     *  目标对象：被代理类
     *  通知：代理时的执行内容
     *      @Before：前置通知，在方法执行前执行
     *      @After：后置通知，在方法执行后执行
     *      @AfterThrowing：异常通知，在方法抛出异常后执行
     *      @AfterReturning：后置返回通知，在方法返回数据前执行
     *      @Around：环绕通知
     *
     *      正常执行(无环绕通知)：
     *          before -> afterReturning -> after
     *      异常执行(无环绕通知)：
     *          before -> afterThrowing -> after
     *
     *      正常执行(环绕通知)：
     *          around before -> before -> afterReturning -> after -> around afterReturning -> around after
     *      异常执行(环绕通知)：
     *          around before -> before -> afterThrowing -> after -> around afterThrowing -> around after
     *
     *  底层依赖的是动态代理
     *      Proxy：基于JDK提供的动态代理类，需要被代理的类实现接口，返回的代理对象与被代理类是兄弟关系
     *      CGLib：不需要被代理类实现接口，返回的代理对象与被代理类是父子关系
     */
    @Test
    public void testAnnotationAspectj() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ioc-15.xml");
        CalculateService calculateService = context.getBean("calculateServiceImpl", CalculateService.class);
        calculateService.add(1, 1);
        System.out.println("-----------------------------------");
        calculateService.sub(1,1);
        System.out.println("-----------------------------------");
        calculateService.mul(1,1);
        System.out.println("-----------------------------------");
        calculateService.div(1,0);
    }

    /**
     * 测试Aspectj xml配置版
     *
     * 执行顺序看around和普通的谁放在前面谁就先代理
     */
    @Test
    public void testXmlAspectj() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ioc-16.xml");
        CalculateService calculateService = context.getBean("calculateService", CalculateService.class);
        calculateService.add(1, 1);
    }
}
