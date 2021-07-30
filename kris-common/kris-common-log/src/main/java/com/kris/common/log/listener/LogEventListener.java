package com.kris.common.log.listener;

import com.kris.common.core.event.KrisEvent;
import com.kris.common.core.listener.KrisEventListener;
import com.kris.common.log.domain.Log;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @Author: chris
 * @Date: 2021/7/29
 * @Description:
 * @Since: JDK11
 */
public class LogEventListener implements KrisEventListener<Log> {

  @Order
  @Async
  @Override
  public void onApplicationEvent(KrisEvent<Log> event) {
    Object source = event.getSource();
    if (source instanceof Log) {
      Log log = (Log) source;
    }
  }
}
