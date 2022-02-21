package com.livk.common.core.converter;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * <p>
 * BaseConverter
 * </p>
 *
 * @param <S> the type parameter
 * @param <T> the type parameter
 * @author livk
 * @date 2021 /11/4
 */
public interface BaseConverter<S, T> {

	/**
	 * Gets source.
	 * @param t the t
	 * @return the source
	 */
	S getSource(T t);

	/**
	 * Gets target.
	 * @param s the s
	 * @return the target
	 */
	T getTarget(S s);

	/**
	 * Stream source stream.
	 * @param ct the ct
	 * @return the stream
	 */
	Stream<S> streamSource(Collection<T> ct);

	/**
	 * Stream target stream.
	 * @param cs the cs
	 * @return the stream
	 */
	Stream<T> streamTarget(Collection<S> cs);

}
