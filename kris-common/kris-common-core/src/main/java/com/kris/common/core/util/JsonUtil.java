package com.kris.common.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;

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
     * @param <T>     the type parameter
     * @param beanStr the bean str
     * @param clazz   the clazz
     * @return t t
     */
    public static <T> T strToBean(String beanStr, Class<T> clazz) {
        T t = null;
        try {
            t = MAPPER.readValue(beanStr, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * Str to collection collection.
     *
     * @param <T>     the type parameter
     * @param beanStr the bean str
     * @param clazz   the clazz
     * @return the collection
     */
    public static <T> Collection<T> strToCollection(String beanStr, Class<T> clazz) {
        Collection<T> t = null;
        try {
            t = MAPPER.readValue(beanStr, new TypeReference<Collection<T>>() {
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
