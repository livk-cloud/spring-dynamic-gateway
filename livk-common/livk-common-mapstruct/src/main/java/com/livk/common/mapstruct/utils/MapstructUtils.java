package com.livk.common.mapstruct.utils;

import com.livk.common.core.support.SpringContextHolder;
import com.livk.common.mapstruct.support.MapstructService;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * <p>
 * MapstructUtils
 * </p>
 *
 * @author livk
 * @date 2022/6/9
 */
@UtilityClass
public class MapstructUtils {

	private static final MapstructService MAPSTRUCT_SERVICE;

	static {
		MAPSTRUCT_SERVICE = SpringContextHolder.getBean(MapstructService.class);
	}

	public <T> T converter(Object source, Class<T> targetClass) {
		return MAPSTRUCT_SERVICE.converter(source, targetClass);
	}

	public <T> Stream<T> converter(Collection<?> sources, Class<T> targetClass) {
		return MAPSTRUCT_SERVICE.converter(sources, targetClass);
	}

}
