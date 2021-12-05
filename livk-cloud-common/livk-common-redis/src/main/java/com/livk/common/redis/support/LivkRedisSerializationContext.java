package com.livk.common.redis.support;

import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import reactor.util.annotation.NonNull;

/**
 * <p>
 * LivkRedisSerializationContext
 * </p>
 *
 * @author livk
 * @date 2021/12/5
 */
public class LivkRedisSerializationContext implements RedisSerializationContext<String, Object> {

    @NonNull
    @Override
    public SerializationPair<String> getKeySerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8);
    }

    @NonNull
    @Override
    public SerializationPair<Object> getValueSerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json());
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public SerializationPair<String> getHashKeySerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public SerializationPair<Object> getHashValueSerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json());
    }

    @NonNull
    @Override
    public SerializationPair<String> getStringSerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8);
    }
}
