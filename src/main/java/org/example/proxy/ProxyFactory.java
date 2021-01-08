package org.example.proxy;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * @Author qiu
 * @Date 2021/1/8 23:21
 */
public class ProxyFactory {

    public static <T> T getJDKProxy(T t) {
        Class<?> clazz = t.getClass();
        Class<?>[] interfaces = clazz.getInterfaces();
        MyProxy myProxy = new MyProxy(t);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, myProxy);
    }

    public static <T> T getCgLibProxy(T t) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(t.getClass());
        enhancer.setCallback(new MyCgLibProxy());
        return (T) enhancer.create();
    }
}
