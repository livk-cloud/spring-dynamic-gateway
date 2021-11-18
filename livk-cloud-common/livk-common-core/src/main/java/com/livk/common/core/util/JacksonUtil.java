package com.livk.common.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The type Jackson util.
 */
@UtilityClass
public class JacksonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * To bean t.
     *
     * @param <T>   the type parameter
     * @param json  the json
     * @param clazz the clazz
     * @return the t
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> T toBean(String json, Class<T> clazz) {
        if (json == null || json.isEmpty() || clazz == null) {
            return null;
        }
        return clazz.isInstance(json) ? (T) json : MAPPER.readValue(json, clazz);
    }

    @SneakyThrows
    public <T> T toBean(InputStream inputStream, Class<T> clazz) {
        if (inputStream == null) {
            return null;
        }
        return MAPPER.readValue(inputStream, clazz);
    }

    /**
     * To stream.
     *
     * @param <T>   the type parameter
     * @param json  the json
     * @param clazz the clazz
     * @return the stream
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> Stream<T> toStream(String json, Class<T> clazz) {
        if (json == null || json.isEmpty() || clazz == null) {
            return Stream.empty();
        }
        var collectionType = MAPPER.getTypeFactory().constructCollectionType(Collection.class, clazz);
        return ((Collection<T>) MAPPER.readValue(json, collectionType)).stream();
    }

    /**
     * To json string.
     *
     * @param obj the obj
     * @return the string
     */
    @SneakyThrows
    public String toJson(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        return obj instanceof String str ? str : MAPPER.writeValueAsString(obj);
    }

    /**
     * To map map.
     *
     * @param <K>    the type parameter
     * @param <V>    the type parameter
     * @param json   the json
     * @param kClass the k class
     * @param vClass the v class
     * @return the map
     */
    @SneakyThrows
    public <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        if (json == null || json.isEmpty() || kClass == null || vClass == null) {
            return Collections.emptyMap();
        }
        var mapType = MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass);
        return MAPPER.readValue(json, mapType);
    }

    @SneakyThrows
    public <K, V> Map<K, V> toMap(byte[] json, Class<K> kClass, Class<V> vClass) {
        if (json == null || kClass == null || vClass == null) {
            return Collections.emptyMap();
        }
        var mapType = MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass);
        return MAPPER.readValue(json, mapType);
    }
}
