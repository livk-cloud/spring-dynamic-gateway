package com.kris.common.mq.events;

/**
 * The interface Message receiver.
 *
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021 /4/17 19:34
 * @since JDK 11
 */
public interface MessageReceiver {

  /**
   * rabbitmq消息接收， 实现此接口、注入ioc容器
   *
   * @param msg the msg
   */
  void handleMessage(String msg);
}
