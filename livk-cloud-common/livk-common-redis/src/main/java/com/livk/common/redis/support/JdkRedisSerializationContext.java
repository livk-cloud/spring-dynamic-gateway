package com.livk.common.redis.support;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.NonNull;

/**
 * <p>
 * JdkRedisSerializationContext
 * </p>
 *
 * @author livk
 * @date 2022/2/21
 */
public class JdkRedisSerializationContext implements RedisSerialization<Object> {

	@NonNull
	@Override
	public SerializationPair<Object> getValueSerializationPair() {
		return SerializationPair.fromSerializer(new JdkSerializationRedisSerializer());
	}

	@NonNull
	@SuppressWarnings("unchecked")
	@Override
	public <HK> SerializationPair<HK> getHashKeySerializationPair() {
		return (SerializationPair<HK>)SerializationPair.fromSerializer(RedisSerializer.string());
	}

	@SuppressWarnings("unchecked")
	@NonNull
	@Override
	public <HV> SerializationPair<HV> getHashValueSerializationPair() {
		return (SerializationPair<HV>)SerializationPair.fromSerializer(new JdkSerializationRedisSerializer());
	}

}
