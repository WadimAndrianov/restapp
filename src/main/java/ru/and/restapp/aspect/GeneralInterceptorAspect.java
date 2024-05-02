package ru.and.restapp.aspect;

import org.aspectj.lang.annotation.*;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GeneralInterceptorAspect {

    private final Logger logger = LoggerFactory.getLogger(GeneralInterceptorAspect.class);

    @Pointcut("execution(* ru.and.restapp.controller.*.*(..)) || execution(* ru.and.restapp.service.*.*(..))")
    public void loggingPointCut() {
    }

    @Pointcut("execution(* ru.and.restapp.service.*.*(..))")
    public void serviceMethods() {
    }

    @Pointcut("execution(* ru.and.restapp.controller.*.*(..))")
    public void controllerMethods() {
    }

    @Before("controllerMethods()")
    public void before(JoinPoint joinPoint) {
        logger.info("Before controller method invoked: {}", joinPoint.getSignature().getName());
    }

    @After("controllerMethods()")
    public void after(JoinPoint joinPoint) {
        logger.info("After controller method invoked: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception occurred in method: {} with message: {}", joinPoint.getSignature().getName(), exception.getMessage());
    }

}


