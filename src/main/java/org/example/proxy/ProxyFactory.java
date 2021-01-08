package org.example.proxy;

import java.lang.reflect.Proxy;

/**
 * @Author qiu
 * @Date 2021/1/8 23:21
 */
public class ProxyFactory {

    public static <T> T getProxy(T t) {
        Class<?> clazz = t.getClass();
        Class<?>[] interfaces = clazz.getInterfaces();
        MyProxy myProxy = new MyProxy(t);
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, myProxy);
    }
}
