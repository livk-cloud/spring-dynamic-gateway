package com.livk.common.core.handler;

import com.livk.common.core.annotation.HandlerType;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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

	private final Class<?> targetClass;

	protected AbstractHandlerAdapter(List<T> targetList) throws NoSuchMethodException {
		this.targetClass = ResolvableType.forConstructorParameter(this.getClass().getConstructor(List.class), 0)
				.resolveGeneric(0);
		handlerMap = targetList.stream()
				.filter(t -> AnnotatedElementUtils.hasAnnotation(t.getClass(), ANNOTATION_TYPE))
				.collect(Collectors.toMap(this::apply,
						Function.identity(),
						(t1, t2) -> t2,
						ConcurrentHashMap::new));
	}

	@SuppressWarnings("unchecked")
	public T get(String value) {
		return (T) Proxy.newProxyInstance(targetClass.getClassLoader(), new Class[]{targetClass},
				(proxy, method, args) -> method.invoke(handlerMap.get(value), args));
	}

	public void put(String key, T t) {
		handlerMap.put(key, t);
	}

	public Set<String> keySet() {
		return handlerMap.keySet();
	}

	private String apply(T t) {
		return Objects.requireNonNull(AnnotationUtils.findAnnotation(t.getClass(), ANNOTATION_TYPE)).value();
	}

}
