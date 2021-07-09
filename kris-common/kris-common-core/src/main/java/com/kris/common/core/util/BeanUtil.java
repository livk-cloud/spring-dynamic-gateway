package com.kris.common.core.util;

import org.springframework.beans.BeanUtils;

/**
 * @Author: kris
 * @Date: 2021/7/9
 * @Description:
 * @Since: JDK11
 */
public final class BeanUtil{

  private BeanUtil() {
  }

  public static <T> T copy(Object source, Class<T> targetClass) {
    try {
      var constructor = targetClass.getConstructor();
      var t = constructor.newInstance();
      BeanUtils.copyProperties(source, t);
      return t;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
