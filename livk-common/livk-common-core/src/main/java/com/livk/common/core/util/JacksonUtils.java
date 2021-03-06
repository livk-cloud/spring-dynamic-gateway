package com.livk.common.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

/**
 * The type Jackson util.
 */
@UtilityClass
public class JacksonUtils {

    /**
     * The constant JSON_EMPTY.
     */
    public static final String JSON_EMPTY = "{}";

    private static final ObjectMapper MAPPER = JsonMapper.builder().build();

    /**
     * To bean t.
     *
     * @param <T>   the type parameter
     * @param json  the json
     * @param clazz the clazz
     * @return the t
     */
    @SuppressWarnings("unchecked")
    @SneakyThrows(JsonProcessingException.class)
    public <T> T toBean(String json, Class<T> clazz) {
        return clazz.isInstance(json) ? (T) json : MAPPER.readValue(json, clazz);
    }

    /**
     * 读取文件反序列化为Bean
     *
     * @param <T>         the type parameter
     * @param inputStream inputStream
     * @param clazz       clazz
     * @return t
     */
    @SneakyThrows(IOException.class)
    public <T> T toBean(InputStream inputStream, Class<T> clazz) {
        return MAPPER.readValue(inputStream, clazz);
    }

    /**
     * 反序列化成集合属性
     *
     * @param <T>   the type parameter
     * @param json  the json
     * @param clazz the clazz
     * @return the stream
     */
    @SuppressWarnings("unchecked")
    @SneakyThrows(JsonProcessingException.class)
    public <T> Stream<T> toStream(String json, Class<T> clazz) {
        var collectionType = MAPPER.getTypeFactory().constructCollectionType(Collection.class, clazz);
        return ((Collection<T>) MAPPER.readValue(json, collectionType)).stream();
    }

    /**
     * To json string.
     *
     * @param obj the obj
     * @return the string
     */
    @SneakyThrows(JsonProcessingException.class)
    public String toJson(Object obj) {
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
    public <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        if (!StringUtils.hasText(json)) {
            return Collections.emptyMap();
        }
        return toMap(json.getBytes(), kClass, vClass);
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
    @SneakyThrows(IOException.class)
    public <K, V> Map<K, V> toMap(byte[] json, Class<K> kClass, Class<V> vClass) {
        var mapType = MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass);
        return MAPPER.readValue(json, mapType);
    }

    @SneakyThrows(JsonProcessingException.class)
    public <T> T toObject(String json, TypeReference<T> valueTypeRef) {
        return MAPPER.readValue(json, valueTypeRef);
    }

    /**
     * 获取json字符串的第一个子节点 从json串中自顶向下依次查找第一个出现的节点
     *
     * @param json     json
     * @param nodeName node
     * @return str node first
     * @example { "c": "1", "a": "2", "b": {"c": 3} } getNodeFirst(json,"c")==>1
     * <p>
     * { "c": "1", "a": "2", "b": {"c": 3,"d":4}, "d": 5 } getNodeFirst(json,"d")==>4
     */
    public JsonNode getNodeFirst(String json, String nodeName) {
        if (!StringUtils.hasText(nodeName)) {
            return null;
        }
        JsonNode jsonNode;
        try {
            jsonNode = MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        if (jsonNode.isArray()) {
            Iterator<JsonNode> elements = jsonNode.elements();
            while (elements.hasNext()) {
                JsonNode result = getNodeFirst(elements.next().toString(), nodeName);
                if (result != null) {
                    return result;
                }
            }
        } else {
            Iterator<String> iterator = jsonNode.fieldNames();
            while (iterator.hasNext()) {
                String node = iterator.next();
                if (node.equals(nodeName)) {
                    return jsonNode.get(nodeName);
                } else {
                    JsonNode child = jsonNode.get(node);
                    if (child.isContainerNode()) {
                        JsonNode result = getNodeFirst(child.toString(), nodeName);
                        if (result != null) {
                            return result;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取json字符串的节点
     *
     * @param json     json
     * @param nodePath node(节点之间以.隔开)
     * @return node node
     * @example { "c": "1", "a": "2", "b": { "c": 3, "d": { "ab": 6 } } } getNode(json,
     * "b"))==>{"c":3,"d":{"ab":6}} getNode(json, "b.c"))==>3 getNode(json,
     * "b.d"))==>{"ab":6} getNode(json, "b.d.ab"))==>6 getNode(json, "d"))==>null
     */
    public JsonNode getNode(String json, String nodePath) {
        if (!StringUtils.hasText(nodePath)) {
            return null;
        }
        JsonNode jsonNode;
        try {
            jsonNode = MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        if (jsonNode.isArray()) {
            Iterator<JsonNode> elements = jsonNode.elements();
            while (elements.hasNext()) {
                JsonNode result = getNode(elements.next().toString(), nodePath);
                if (result != null) {
                    return result;
                }
            }
        } else {
            int index = nodePath.indexOf(".");
            Iterator<String> iterator = jsonNode.fieldNames();
            while (iterator.hasNext()) {
                String node = iterator.next();
                if (index <= 0) {
                    if (node.equals(nodePath)) {
                        return jsonNode.get(nodePath);
                    }
                } else {
                    String parentNode = nodePath.substring(0, index);
                    if (node.equals(parentNode)) {
                        JsonNode child = jsonNode.get(node);
                        if (child.isContainerNode()) {
                            String childNode = nodePath.substring(index + 1);
                            JsonNode result = getNode(child.toString(), childNode);
                            if (result != null) {
                                return result;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

}
