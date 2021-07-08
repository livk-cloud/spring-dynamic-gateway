package com.kris.common.actuator;

import com.kris.common.actuator.config.SecurityProperties;
import com.kris.common.actuator.security.UserConfigurer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/7/7 22:23
 * @since JDK 11
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SecurityProperties.class)
@EnableAutoConfiguration
public class ActuatorAutoConfig {

  @Bean
  public UserConfigurer userConfig(SecurityProperties properties) {
    return new UserConfigurer(properties);
  }
}
