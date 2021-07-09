package com.kris.common.core.util;

import java.lang.reflect.Constructor;
import org.springframework.beans.BeanUtils;

/**
 * @Author: kris
 * @Date: 2021/7/9
 * @Description:
 * @Since: JDK11
 */
public class BeanUtil {

  private BeanUtil() {
  }

  public static <T> T copy(Object source, Class<T> targetClass) {
    try {
      Constructor<T> constructor = targetClass.getConstructor();
      T t = constructor.newInstance();
      BeanUtils.copyProperties(source, t);
      return t;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
