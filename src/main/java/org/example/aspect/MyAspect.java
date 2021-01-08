package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author qiu
 * @Date 2021/1/6 20:49
 */
@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(* org.example.service..*(..))")
    public void pointCut() {}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        System.out.println(signature.getName() + " is before...... args: " + Arrays.toString(args));
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        Signature signature = joinPoint.getSignature();
        System.out.println(signature.getName() + " is afterReturning...... result: " + result);
    }

    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        Signature signature = joinPoint.getSignature();
        System.out.println(signature.getName() + " is afterThrowing...... e: " + e.getMessage());
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println(signature.getName() + " is after......");
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        Object result;
        try {
            Object[] args = joinPoint.getArgs();
            System.out.println(signature.getName() + " is around before...... args: " + Arrays.toString(args));
            result = joinPoint.proceed(args);
            System.out.println(signature.getName() + " is around afterReturning...... result: " + Arrays.toString(args));
        } catch (Throwable throwable) {
            System.out.println(signature.getName() + " is around afterThrowing...... e: " + throwable.getMessage());
            throw throwable;
        } finally {
            System.out.println(signature.getName() + " is around after......");
        }
        return result;
    }
}
