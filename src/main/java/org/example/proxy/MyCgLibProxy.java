package org.example.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author qiu
 * @Date 2021/1/9 3:24
 */
public class MyCgLibProxy<T> implements MethodInterceptor {

//    private final T t;
//    public MyCgLibProxy(T t) {
//        this.t = t;
//    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        String methodName = method.getName();
        try {
            System.out.println(methodName + " is before by cglib proxy...... args: " + Arrays.toString(objects));
            result = methodProxy.invokeSuper(o, objects);
            System.out.println(methodName + " is afterReturning by cglib proxy...... result: " + result);
        } catch (Exception e) {
            System.out.println(methodName + " is afterThrowing by cglib proxy...... e: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println(methodName + " is after by cglib proxy......");
        }
        return result;
    }
}
