package com.kris.common.mq.events;

import com.kris.common.mq.config.RabbitProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 19:34
 * @since JDK 11
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventSender {

    private final RabbitTemplate rabbitTemplate;

    private final MessageConverter messageConverter;

    private final RabbitProperties properties;

    @PostConstruct
    public void init() {
        rabbitTemplate.setMessageConverter(messageConverter);
    }

    public void send(Object obj) {
        rabbitTemplate.convertAndSend(properties.getExchange(), properties.getBinding(), obj);
    }
}
