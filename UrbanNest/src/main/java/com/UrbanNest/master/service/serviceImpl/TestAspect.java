package com.UrbanNest.master.service.serviceImpl;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAspect {
    @Before("execution(* com.UrbanNest.master.service.serviceImpl.UnitServiceImpl.*(..))")

    public void logBeforeMethod() {
        System.out.println("Method is about to be executed...");
    }
}
