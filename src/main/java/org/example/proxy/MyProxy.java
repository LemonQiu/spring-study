package org.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author qiu
 * @Date 2021/1/6 20:52
 */
public class MyProxy<T> implements InvocationHandler {

    private final T t;

    public MyProxy(T t) {
        this.t = t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        String methodName = method.getName();
        try {
            System.out.println(methodName + "执行开始...... args: " + Arrays.toString(args));
            result = method.invoke(t, args);
            System.out.println(methodName + "执行结束...... result: " + result);
        } catch (Exception e) {
            System.out.println(methodName + "执行异常...... e: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println(methodName + "执行finally......");
        }
        return result;
    }
}
