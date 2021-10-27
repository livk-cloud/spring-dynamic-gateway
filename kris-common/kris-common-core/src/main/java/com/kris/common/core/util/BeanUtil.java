package com.kris.common.core.util;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
 * @Author: kris @Date: 2021/7/9 @Description: @Since: JDK11
 */
public final class BeanUtil {

    private BeanUtil() {
    }

    /**
     * 基于BeanUtils的复制
     *
     * @param source      目标源
     * @param targetClass 需复制的结果类型
     * @param <T>         类型
     * @return result
     */
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

    /**
     * list类型复制
     *
     * @param sourceList  目标list
     * @param targetClass class类型
     * @param <T>         类型
     * @return result list
     */
    public static <T> List<T> copyList(Collection<Object> sourceList, Class<T> targetClass) {
        return sourceList.stream().map(source -> copy(source, targetClass)).toList();
    }
}
