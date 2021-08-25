package com.kris.common.redis.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * The interface Redis service.
 *
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021 /4/17 12:02
 * @since JDK 11
 */
public interface RedisService {

  /**
   * List all list.
   *
   * @param prefix the prefix
   * @return the list
   */
  List<Object> listAll(String prefix);

  /**
   * Gets by key.
   *
   * @param key the key
   * @return the by key
   */
  Object getByKey(String key);

  /**
   * Insert boolean.
   *
   * @param obj the obj
   * @param key the key
   * @return the boolean
   */
  boolean insertOrUpdate(Object obj, String key);

  /**
   * Delete boolean.
   *
   * @param keys the keys
   * @return the boolean
   */
  boolean delete(Collection<String> keys);

  /**
   * Delete boolean.
   *
   * @param keys the keys
   * @return the boolean
   */
  boolean delete(String... keys);

  /**
   * Keys set.
   *
   * @param prefix the prefix
   * @return the set
   */
  Set<String> keys(String prefix);
}
