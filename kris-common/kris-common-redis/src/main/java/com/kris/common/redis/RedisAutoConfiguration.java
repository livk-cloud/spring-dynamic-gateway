package com.kris.common.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kris.common.redis.service.RedisService;
import com.kris.common.redis.service.impl.RedisServiceImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>
 * RedisAutoConfiguration
 * </p>
 *
 * @author livk
 * @date 2021/9/11
 */
@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration
public class RedisAutoConfiguration {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        // 泛型改成 String Object，方便我们的使用
        var template = new RedisTemplate<String, Object>();
        // Json序列化配置
        // 使用 json解析对象
        var serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 通过 ObjectMapper进行转义
        var mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        serializer.setObjectMapper(mapper);
        var stringRedisSerializer = new StringRedisSerializer();
        // key采用 String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的 key也采用 String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // Value的序列化方式采用 jackSon
        template.setValueSerializer(serializer);
        // hash的 value序列化也采用 jackSon
        template.setHashValueSerializer(serializer);
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisService redisService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisServiceImpl(redisTemplate);
    }
}
