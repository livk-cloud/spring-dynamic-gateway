package com.livk.common.redis.support;

import com.livk.common.redis.util.SerializerUtils;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
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

    private LivkRedisTemplate() {
        var serializer = SerializerUtils.getJacksonSerializer(Object.class);
        var stringRedisSerializer = new StringRedisSerializer();
        this.setKeySerializer(stringRedisSerializer);
        this.setHashKeySerializer(stringRedisSerializer);
        this.setValueSerializer(serializer);
        this.setHashValueSerializer(serializer);
    }

    public LivkRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        this();
        this.setConnectionFactory(redisConnectionFactory);
        this.afterPropertiesSet();
    }
}
