package com.livk.common.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * JacksonUtil
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@UtilityClass
public class JacksonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Str to bean t.
     *
     * @param <T>   the type parameter
     * @param json  the bean str
     * @param clazz the clazz
     * @return t t
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> T strToBean(String json, Class<T> clazz) {
        if (json == null || json.isEmpty() || clazz == null) {
            return null;
        }
        return clazz.isInstance(json) ? (T) json : MAPPER.readValue(json, clazz);
    }

    /**
     * Str to collection.
     *
     * @param <T>   the type parameter
     * @param json  the bean str
     * @param clazz the clazz
     * @return the collection
     */
    @SneakyThrows
    public <T> List<T> strToCollection(String json, Class<T> clazz) {
        if (json == null || json.isEmpty() || clazz == null) {
            return Collections.emptyList();
        }
        var collectionType = MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
        return MAPPER.readValue(json, collectionType);
    }

    /**
     * Obj to str string.
     *
     * @param obj the obj
     * @return the string
     */
    @SneakyThrows
    public String objToStr(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        return obj instanceof String str ? str : MAPPER.writeValueAsString(obj);
    }

    /**
     * Str to map map.
     *
     * @param <K>    the type parameter
     * @param <V>    the type parameter
     * @param json   the json
     * @param kClass the k class
     * @param vClass the v class
     * @return the map
     */
    @SneakyThrows
    public <K, V> Map<K, V> strToMap(String json, Class<K> kClass, Class<V> vClass) {
        if (json == null || json.isEmpty() || kClass == null || vClass == null) {
            return Collections.emptyMap();
        }
        var mapType = MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass);
        return MAPPER.readValue(json, mapType);
    }

    /**
     * Find object string.
     *
     * @param obj  the obj
     * @param name the name
     * @return the string
     */
    @SneakyThrows
    public String findObject(Object obj, String name) {
        var objToStr = objToStr(obj);
        return MAPPER.readTree(objToStr).findPath(name).asText();
    }
}
