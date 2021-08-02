package com.kris.common.log;

import com.kris.common.log.aspect.LogAspect;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/** @Author: kris @Date: 2021/7/12 @Description: @Since: JDK11 */
@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration
public class LogAutoConfiguration {

  @Bean
  public LogAspect logAspect(Environment environment) {
    return new LogAspect(environment);
  }
}
