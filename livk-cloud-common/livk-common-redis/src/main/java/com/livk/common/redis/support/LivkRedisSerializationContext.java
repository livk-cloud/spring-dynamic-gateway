package com.livk.common.redis.support;

import com.livk.common.redis.util.SerializerUtils;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
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

    private final Jackson2JsonRedisSerializer<Object> serializer;

    public LivkRedisSerializationContext() {
        this.serializer = SerializerUtils.getJacksonSerializer(Object.class);
    }

    @NonNull
    @Override
    public SerializationPair<String> getKeySerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string());
    }

    @NonNull
    @Override
    public SerializationPair<Object> getValueSerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(serializer);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public SerializationPair<String> getHashKeySerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string());
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public SerializationPair<Object> getHashValueSerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(serializer);
    }

    @NonNull
    @Override
    public SerializationPair<String> getStringSerializationPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string());
    }
}
