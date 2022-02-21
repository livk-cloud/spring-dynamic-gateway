package com.livk.common.redis.support;

import com.livk.common.redis.util.SerializerUtils;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.lang.NonNull;

/**
 * <p>
 * LivkRedisSerializationContext
 * </p>
 *
 * @author livk
 * @date 2021/12/5
 */
public class Jackson2RedisSerializationContext<T> implements RedisSerialization<T> {

	private final Jackson2JsonRedisSerializer<T> serializer;

	public Jackson2RedisSerializationContext(Class<T> targetClass) {
		this.serializer = SerializerUtils.getJacksonSerializer(targetClass);
	}

	@NonNull
	@Override
	public SerializationPair<T> getValueSerializationPair() {
		return RedisSerializationContext.SerializationPair.fromSerializer(serializer);
	}

	@SuppressWarnings("unchecked")
	@NonNull
	@Override
	public SerializationPair<T> getHashValueSerializationPair() {
		return SerializationPair.fromSerializer(serializer);
	}

}
