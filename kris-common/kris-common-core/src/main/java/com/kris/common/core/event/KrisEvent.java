package com.kris.common.core.event;

import org.springframework.context.ApplicationEvent;

/** @Author: chris @Date: 2021/7/30 @Description: @Since: JDK11 */
public class KrisEvent<T> extends ApplicationEvent {

  public KrisEvent(T t) {
    super(t);
  }
}
