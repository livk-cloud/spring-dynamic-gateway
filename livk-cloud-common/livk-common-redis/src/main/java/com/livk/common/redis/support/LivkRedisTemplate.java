package com.livk.common.redis.support;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>
 * LivkRedisTemplate
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
public class LivkRedisTemplate extends RedisTemplate<String, Object> {

    public LivkRedisTemplate() {
        var serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 通过 ObjectMapper进行转义
        var mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        serializer.setObjectMapper(mapper);
        var stringRedisSerializer = new StringRedisSerializer();
        // key采用 String的序列化方式
        this.setKeySerializer(stringRedisSerializer);
        // hash的 key也采用 String的序列化方式
        this.setHashKeySerializer(stringRedisSerializer);
        // Value的序列化方式采用 jackSon
        this.setValueSerializer(serializer);
        // hash的 value序列化也采用 jackSon
        this.setHashValueSerializer(serializer);
    }

    public LivkRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        this();
        this.setConnectionFactory(redisConnectionFactory);
        this.afterPropertiesSet();
    }
}
