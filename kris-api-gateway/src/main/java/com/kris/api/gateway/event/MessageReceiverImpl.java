package com.kris.api.gateway.event;

import com.kris.api.gateway.handler.RouteHandler;
import com.kris.common.core.util.JacksonUtil;
import com.kris.common.mq.domain.RouteMessage;
import com.kris.common.mq.events.MessageReceiver;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: kris
 * @Date: 2021/7/15
 * @Description:
 * @Since: JDK11
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MessageReceiverImpl implements MessageReceiver {

  private final RouteHandler routeHandler;

  @Override
  public void handleMessage(String msg) {
    var routeMessage = JacksonUtil.strToBean(msg, RouteMessage.class);
    switch (Objects.requireNonNull(routeMessage).getType()) {
      case INSERT:
        routeHandler.save(routeMessage.getRoutingKey());
        break;
      case UPDATE:
        routeHandler.update(routeMessage.getRoutingKey());
        break;
      case DELETE:
        routeHandler.delete(routeMessage.getRoutingKey());
        break;
      case NONE:
        routeHandler.refresh();
        break;
      default:
        log.info("Unknown operation type！");
        break;
    }
  }
}
