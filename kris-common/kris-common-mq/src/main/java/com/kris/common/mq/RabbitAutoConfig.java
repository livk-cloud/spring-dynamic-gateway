package com.kris.common.mq;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kris.common.mq.config.RabbitProperties;
import com.kris.common.mq.events.BusReceiver;
import com.kris.common.mq.events.EventSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 11:37
 * @since JDK 11
 */
@Slf4j
@Configuration
@Import({EventSender.class})
@EnableAutoConfiguration
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitAutoConfig {

    public static final String QUEUE_NAME = "kris-queue";
    public static final String EXCHANGE_NAME = "kris-exchange";
    public static final String BINDING_NAME = "kris-binding";

    @Bean
    public Queue queue(RabbitProperties properties) {
        log.info("queue name:{}", properties.getQueue());
        return new Queue(properties.getQueue(), false);
    }

    @Bean
    public TopicExchange exchange(RabbitProperties properties) {
        log.info("exchange:{}", properties.getExchange());
        return new TopicExchange(properties.getExchange());
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange,RabbitProperties properties) {
        log.info("binding {} to {} with {}", queue, exchange, properties.getBinding());
        return BindingBuilder.bind(queue).to(exchange).with(properties.getBinding());
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter, Queue queue) {
        log.info("init simpleMessageListenerContainer: {}", queue.getName());
        var container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(queue.getName());
        container.setMessageListener(messageListenerAdapter);
        return container;
    }

    @Bean
    @ConditionalOnBean(BusReceiver.class)
    public MessageListenerAdapter messageListenerAdapter(BusReceiver busReceiver, MessageConverter messageConverter) {
        log.info("new listener");
        return new MessageListenerAdapter(busReceiver, messageConverter);
    }

    @Bean
    public MessageConverter messageConverter() {
        var objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        return new ContentTypeDelegatingMessageConverter(new Jackson2JsonMessageConverter(objectMapper));
    }
}
