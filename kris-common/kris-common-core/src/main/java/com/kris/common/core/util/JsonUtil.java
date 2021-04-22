package com.kris.common.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Map;

/**
 * The type Bean util.
 *
 * @Author: kris
 * @Date: 2021 /4/19
 * @Description:
 * @Since: JDK11
 */
public class JsonUtil {

    private JsonUtil() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Str to bean t.
     *
     * @param <T>   the type parameter
     * @param json  the bean str
     * @param clazz the clazz
     * @return t t
     */
    public static <T> T strToBean(String json, Class<T> clazz) {
        T t = null;
        try {
            t = MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * Str to collection collection.
     *
     * @param <T>   the type parameter
     * @param json  the bean str
     * @param clazz the clazz
     * @return the collection
     */
    public static <T> Collection<T> strToCollection(String json, Class<T> clazz) {
        Collection<T> t = null;
        try {
            t = MAPPER.readValue(json, new TypeReference<Collection<T>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * Obj to bean t.
     *
     * @param <T>   the type parameter
     * @param obj   the obj
     * @param clazz the clazz
     * @return the t
     */
    public static <T> T objToBean(Object obj, Class<T> clazz) {
        return strToBean(objToStr(obj), clazz);
    }


    /**
     * Obj to str string.
     *
     * @param obj the obj
     * @return the string
     */
    public static String objToStr(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
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
    public static <K, V> Map<K, V> strToMap(String json, Class<K> kClass, Class<V> vClass) {
        Map<K, V> map = null;
        try {
            map = MAPPER.readValue(json, new TypeReference<Map<K, V>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Find object string.
     *
     * @param obj  the obj
     * @param name the name
     * @return the string
     */
    public static String findObject(Object obj, String name) {
        String objToStr = objToStr(obj);
        try {
            JsonNode jsonNode = MAPPER.readTree(objToStr);
            return jsonNode.findPath(name).asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
