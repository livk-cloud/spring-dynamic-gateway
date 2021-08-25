package com.kris.common.mq.events;

import com.kris.common.mq.config.RabbitProperties;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 19:34
 * @since JDK 11
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MessageSender {

  private final RabbitTemplate rabbitTemplate;

  private final MessageConverter messageConverter;

  private final RabbitProperties properties;

  @PostConstruct
  public void init() {
    rabbitTemplate.setMessageConverter(messageConverter);
  }

  public boolean send(Object obj) {
    try {
      rabbitTemplate.convertAndSend(properties.getExchange(), properties.getBinding(), obj);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
