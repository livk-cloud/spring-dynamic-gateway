package com.livk.common.core.hadnler;

import com.livk.common.core.annotation.HandlerType;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * AbstractHandlerAdapter
 * </p>
 *
 * @author livk
 * @date 2022/2/19
 */
public abstract class AbstractHandlerAdapter<T> {

	public static final Class<HandlerType> ANNOTATION_TYPE = HandlerType.class;

	protected final Map<String, T> handlerMap;

	protected AbstractHandlerAdapter(List<T> targetList) {
		handlerMap = targetList.stream().filter(t -> AnnotationUtils.isCandidateClass(t.getClass(), ANNOTATION_TYPE))
				.collect(Collectors.toMap(t -> t.getClass().getAnnotation(ANNOTATION_TYPE).value(), Function.identity(),
						(t1, t2) -> t2, () -> Collections.synchronizedMap(new LinkedHashMap<>())));
	}

	public T get(String value) {
		return handlerMap.get(value);
	}

	public Set<String> keySet() {
		return handlerMap.keySet();
	}

}
