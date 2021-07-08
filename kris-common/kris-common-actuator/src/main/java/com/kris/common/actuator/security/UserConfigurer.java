package com.kris.common.actuator.security;

import com.kris.common.actuator.config.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/7/7 22:26
 * @since JDK 11
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserConfigurer extends WebSecurityConfigurerAdapter {

  private final SecurityProperties securityProperties;

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser(securityProperties.getUsername())
        .password(securityProperties.getPassword())
        .roles("admin");
  }
}
