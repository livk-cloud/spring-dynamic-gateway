package com.livk.common.core.converter;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * <p>
 * BaseConverter
 * </p>
 *
 * @author livk
 * @date 2021/11/4
 */
public interface BaseConverter<S, T> {
    S getSource(T t);

    T getTarget(S s);

    Stream<S> streamSource(Collection<T> ts);

    Stream<T> streamTarget(Collection<S> s);
}
