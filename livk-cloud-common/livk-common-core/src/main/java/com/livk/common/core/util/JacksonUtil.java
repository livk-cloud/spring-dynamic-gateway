package com.livk.common.core.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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

    /**
     * 获取json字符串的第一个子节点
     * 从json串中自顶向下依次查找第一个出现的节点
     *
     * @param json     json
     * @param nodeName node
     * @return str
     * @example {
     * "c": "1",
     * "a": "2",
     * "b": {"c": 3}
     * }   getNodeFirst(json,"c")==>1
     * <p>
     * {
     * "c": "1",
     * "a": "2",
     * "b": {"c": 3,"d":4},
     * "d": 5
     * }    getNodeFirst(json,"d")==>4
     */
    @SneakyThrows
    public JsonNode getNodeFirst(String json, String nodeName) {
        if (!StringUtils.hasText(json) || !StringUtils.hasText(nodeName)) {
            return null;
        }
        JsonNode jsonNode = MAPPER.readTree(json);
        if (jsonNode.isArray() && jsonNode instanceof ArrayNode arrayNode) {
            Iterator<JsonNode> elements = arrayNode.elements();
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
                    if (child.isArray() || child.isObject()) {
                        return getNodeFirst(child.toString(), nodeName);
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
     * @return node
     * @example {
     * "c": "1",
     * "a": "2",
     * "b": {
     * "c": 3,
     * "d": {
     * "ab": 6
     * }
     * }
     * }
     * getNode(json, "b"))==>{"c":3,"d":{"ab":6}}
     * getNode(json, "b.c"))==>3
     * getNode(json, "b.d"))==>{"ab":6}
     * getNode(json, "b.d.ab"))==>6
     * getNode(json, "d"))==>null
     */
    @SneakyThrows
    public JsonNode getNode(String json, String nodePath) {
        if (!StringUtils.hasText(json) || !StringUtils.hasText(nodePath)) {
            return null;
        }
        JsonNode jsonNode = MAPPER.readTree(json);
        if (jsonNode.isArray() && jsonNode instanceof ArrayNode arrayNode) {
            Iterator<JsonNode> elements = arrayNode.elements();
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
                        if (child.isArray() || child.isObject()) {
                            String childNode = nodePath.substring(index + 1);
                            return getNode(child.toString(), childNode);
                        }
                    }
                }
            }
        }
        return null;
    }

    public JsonNode getNodeNo(String json, String nodeName, int no) {
        return null;
    }

    public static void main(String[] args) {
        String json = """
                {
                  "a": [
                    {
                      "b": {
                        "c": "dd"
                      }
                    },
                    {
                      "b": {
                        "c": "cc"
                      }
                    }
                  ]
                }""";
        System.out.println(JacksonUtil.getNode(json, "a"));
        System.out.println(JacksonUtil.getNode(json, "a.b"));
        System.out.println(JacksonUtil.getNode(json, "a.b.c"));
//        System.out.println(JacksonUtil.getNode(json, "b.d.ab"));
//        System.out.println(JacksonUtil.getNode(json, "d"));
    }
}
