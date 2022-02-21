package com.livk.common.redis.support;

import com.livk.common.redis.util.SerializerUtils;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.NonNull;

/**
 * <p>
 * LivkRedisSerializationContext
 * </p>
 *
 * @author livk
 * @date 2021/12/5
 */
public class Jackson2RedisSerializationContext<T> implements RedisSerializationContext<String, T> {

    private final Jackson2JsonRedisSerializer<T> serializer;

    public Jackson2RedisSerializationContext(Class<T> targetClass) {
        this.serializer = SerializerUtils.getJacksonSerializer(targetClass);
    }

    @NonNull
    @Override
    public SerializationPair<String> getKeySerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string());
    }

    @NonNull
    @Override
    public SerializationPair<T> getValueSerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(serializer);
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public SerializationPair<String> getHashKeySerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string());
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public SerializationPair<T> getHashValueSerializationPair() {
        return SerializationPair.fromSerializer(serializer);
    }

    @NonNull
    @Override
    public SerializationPair<String> getStringSerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string());
    }
}
