package com.livk.common.redis.support;

import com.livk.common.redis.util.SerializerUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;

/**
 * <p>
 * LivkRedisSerializationContext
 * </p>
 *
 * @author livk
 * @date 2021/12/5
 */
public class Jackson2RedisSerializationContext<T> implements RedisSerialization<T> {

	private final RedisSerializer<T> serializer;

	public Jackson2RedisSerializationContext(Class<T> targetClass) {
		this.serializer = SerializerUtils.getJacksonSerializer(targetClass);
	}

	@Nonnull
	@Override
	public SerializationPair<T> getValueSerializationPair() {
		return SerializationPair.fromSerializer(serializer);
	}

	@SuppressWarnings("unchecked")
	@Nonnull
	@Override
	public <HV> SerializationPair<HV> getHashValueSerializationPair() {
		return (SerializationPair<HV>) SerializationPair.fromSerializer(serializer);
	}

}
