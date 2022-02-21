package com.livk.common.redis.support;

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * <p>
 * LivkReactiveRedisTemplate
 * </p>
 *
 * @author livk
 * @date 2021/12/5
 */
public class LivkReactiveRedisTemplate extends ReactiveRedisTemplate<String, Object> {
    public LivkReactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        this(connectionFactory, new JacksonRedisSerializationContext<>(Object.class));
    }

    public LivkReactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory, RedisSerializationContext<String, Object> serializationContext) {
        super(connectionFactory, serializationContext);
    }

    public LivkReactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory, RedisSerializationContext<String, Object> serializationContext, boolean exposeConnection) {
        super(connectionFactory, serializationContext, exposeConnection);
    }
}
