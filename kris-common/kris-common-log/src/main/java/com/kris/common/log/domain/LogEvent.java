package com.kris.common.log.domain;

import java.net.InetAddress;
import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: kris
 * @Date: 2021/7/12
 * @Description:
 * @Since: JDK11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class LogEvent extends ApplicationEvent {

  private String service;

  private String username;

  private String method;

  private String description;

  private transient Map<String, Object> params;

  private transient Object result;

  private InetAddress ip;

  private Long runtime;
}
