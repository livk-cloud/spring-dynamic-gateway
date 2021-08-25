package com.kris.common.core.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.Serializable;
import java.util.Map;
import org.springframework.util.ObjectUtils;

/** The interface Json. @Author: kris @Date: 2021 /7/22 @Description: @Since: JDK11 */
public interface Json extends Serializable {

  /** The constant MAPPER. */
  ObjectMapper MAPPER = new ObjectMapper();
  /** The constant TYPE_FACTORY. */
  TypeFactory TYPE_FACTORY = MAPPER.getTypeFactory();

  /**
   * To json string.
   *
   * @return the string
   */
  default String toJson() {
    if (ObjectUtils.isEmpty(this)) {
      return null;
    }
    try {
      return MAPPER.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * To map map.
   *
   * @return the map
   */
  default Map<String, Object> toMap() {
    try {
      var mapType = TYPE_FACTORY.constructMapType(Map.class, String.class, Object.class);
      return MAPPER.readValue(this.toJson(), mapType);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Str to bean json.
   *
   * @param str the str
   * @return the json
   */
  public static Json strToBean(String str) {
    try {
      return MAPPER.readValue(str, Json.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return null;
    }
  }
}
