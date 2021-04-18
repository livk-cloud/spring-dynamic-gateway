package com.kris.common.log.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 13:15
 * @since JDK 11
 */
@Aspect
@Component
@EnableAutoConfiguration
public class LogAutoConfig {

    @Pointcut("@annotation(com.kris.common.log.anno.Log)")
    public void pointCut(){

    }

    @Before("pointCut()")
    public void beforeLog(){

    }

    @After("pointCut()")
    public void afterLog(){

    }

}
