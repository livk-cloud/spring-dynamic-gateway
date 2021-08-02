package com.kris.common.log.domain;

import java.net.InetAddress;
import java.util.Map;
import lombok.Data;

/** @Author: kris @Date: 2021/7/12 @Description: @Since: JDK11 */
@Data
public class Log {

  private String service;

  private String username;

  private String method;

  private String description;

  private Map<String, Object> params;

  private Object result;

  private InetAddress ip;

  private Long runtime;
}
