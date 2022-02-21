package com.livk.common.core.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * <p>
 * StringUtils
 * </p>
 *
 * @author livk
 * @date 2021/12/24
 */
@UtilityClass
public class ObjectUtils extends org.springframework.util.ObjectUtils {

    @SafeVarargs
    public <T> boolean allChecked(Predicate<T> predicate, T... ts) {
        return Stream.of(ts).allMatch(predicate);
    }

    public <T> boolean allChecked(Predicate<T> predicate, Collection<T> collection) {
        return collection.stream().allMatch(predicate);
    }

    @SafeVarargs
    public <T> boolean anyChecked(Predicate<T> predicate, T... ts) {
        return Stream.of(ts).anyMatch(predicate);
    }

    public <T> boolean anyChecked(Predicate<T> predicate, Collection<T> collection) {
        return collection.stream().anyMatch(predicate);
    }
}
