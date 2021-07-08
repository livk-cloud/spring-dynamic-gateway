package com.kris.common.actuator.security;

import com.kris.common.actuator.config.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/7/7 22:32
 * @since JDK 11
 */
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WebPageConfigurer extends WebSecurityConfigurerAdapter {

  private final SecurityProperties securityProperties;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    successHandler.setTargetUrlParameter("redirectTo");
    // 静态资源和登录页面可以不用认证
    http.authorizeRequests().antMatchers(securityProperties.getContextPath() + "/assets/**")
        .permitAll()
        .antMatchers(securityProperties.getContextPath() + "/login").permitAll()
        // 其他请求必须认证
        .anyRequest().authenticated()
        // 自定义登录和退出
        .and().formLogin().loginPage(securityProperties.getContextPath() + "/login")
        .successHandler(successHandler).and().logout()
        .logoutUrl(securityProperties.getContextPath() + "/logout")
        // 启用HTTP-Basic, 用于Spring Boot Admin Client注册
        .and().httpBasic().and().csrf().disable();
  }
}
