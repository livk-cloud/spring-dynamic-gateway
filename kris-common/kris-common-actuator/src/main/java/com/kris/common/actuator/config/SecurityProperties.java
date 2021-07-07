package com.kris.common.actuator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/7/7 22:18
 * @since JDK 11
 */
@Data
@RefreshScope
@ConfigurationProperties("kris.security-actuator")
public class SecurityProperties {

  private String username;

  private String password;

  private String contextPath;
}
