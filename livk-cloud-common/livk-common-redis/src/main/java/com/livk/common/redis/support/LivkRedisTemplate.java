package com.livk.common.redis.support;

import com.livk.common.redis.util.SerializerUtils;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

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
		this.setKeySerializer(RedisSerializer.string());
		this.setHashKeySerializer(RedisSerializer.string());
		this.setValueSerializer(SerializerUtils.getJacksonSerializer(Object.class));
		this.setHashValueSerializer(SerializerUtils.getJacksonSerializer(Object.class));
	}

	public LivkRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		this();
		this.setConnectionFactory(redisConnectionFactory);
		this.afterPropertiesSet();
	}

}
