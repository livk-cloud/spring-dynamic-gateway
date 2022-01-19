package com.livk.common.redis.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * <p>
 * JacksonRedisUtils
 * </p>
 *
 * @author livk
 * @date 2022/1/19
 */
@UtilityClass
public class SerializerUtils {

    public <T> Jackson2JsonRedisSerializer<T> getJacksonSerializer(Class<T> targetClass) {
        var serializer = new Jackson2JsonRedisSerializer<>(targetClass);
        var mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        serializer.setObjectMapper(mapper);
        return serializer;
    }
}
