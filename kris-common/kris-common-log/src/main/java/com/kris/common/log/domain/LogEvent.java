package com.kris.common.log.domain;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: kris
 * @Date: 2021/7/15
 * @Description:
 * @Since: JDK11
 */
public class LogEvent extends ApplicationEvent {

  public LogEvent(Log log) {
    super(log);
  }
}
