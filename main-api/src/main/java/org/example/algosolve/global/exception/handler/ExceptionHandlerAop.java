package org.example.algosolve.global.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class ExceptionHandlerAop {

    @Around("execution(* org.example.algosolve.global.exception.handler.GlobalExceptionHandler.*(..))")
    public Object loggin(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args.length == 1 && args[0] instanceof Exception) {
            log.error("", (Exception) args[0]);
        }
        return joinPoint.proceed();
    }
}
