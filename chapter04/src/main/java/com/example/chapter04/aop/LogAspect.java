package com.example.chapter04.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    //@Before("execution(* com.example.chapter04.service..*(..))")
    @Before("execution(* com.example.chapter04.service..order(..))")
    public void beforeLog() {
        System.out.println("[LOG] 메서드 실행 전");
    }

    //@After("execution(* com.example.chapter04.service..*(..))")
    @After("execution(* com.example.chapter04.service..order(..))")
    public void afterLog() {
        System.out.println("[LOG] 메서드 실행 후");
    }

    @Around("execution(* com.example.chapter04.service..*(..))")
    public Object timeCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();

        Object result = joinPoint.proceed();

        long end = System.nanoTime();
        System.out.println("[TIME] 실행 시간: " + (end - start) + "ms");

        return result;
    }

}
