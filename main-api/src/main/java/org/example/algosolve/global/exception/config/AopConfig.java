package org.example.algosolve.global.exception.config;

import org.example.algosolve.global.exception.handler.ExceptionHandlerAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    @Bean
    public ExceptionHandlerAop exceptionHandlerAop(){
        return new ExceptionHandlerAop();
    }
}
