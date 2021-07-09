package com.kris.common.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ObjectUtils;

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
public final class JacksonUtil {

  private JacksonUtil() {
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
  @SuppressWarnings("unchecked")
  public static <T> T strToBean(String json, Class<T> clazz) {
    T t = null;
    if (json.isEmpty() || clazz == null) {
      return null;
    }
    try {
      t = clazz.equals(String.class) ? (T) json : MAPPER.readValue(json, clazz);
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
      var collectionType = MAPPER.getTypeFactory().constructCollectionType(Collection.class, clazz);
      t = MAPPER.readValue(json, collectionType);
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
    if (ObjectUtils.isEmpty(new Object[]{obj, clazz})) {
      return null;
    }
    String json = objToStr(obj);
    if (ObjectUtils.isEmpty(json)) {
      return null;
    }
    return strToBean(json, clazz);
  }


  /**
   * Obj to str string.
   *
   * @param obj the obj
   * @return the string
   */
  public static String objToStr(Object obj) {
    String json = null;
    if (ObjectUtils.isEmpty(obj)) {
      return null;
    }
    try {
      json = obj instanceof String ? (String) obj : MAPPER.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
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
      var mapType = MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass);
      map = MAPPER.readValue(json, mapType);
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
      return MAPPER.readTree(objToStr).findPath(name).asText();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Is empty boolean.
   *
   * @param obj the obj
   * @return the boolean
   */
  public static boolean isEmpty(Object... obj) {
    return ObjectUtils.isEmpty(obj);
  }
}
