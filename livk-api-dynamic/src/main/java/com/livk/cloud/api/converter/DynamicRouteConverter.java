package com.livk.cloud.api.converter;

import com.livk.cloud.api.domain.DynamicRoute;
import com.livk.cloud.api.domain.RedisRoute;
import com.livk.cloud.api.domain.route.Filter;
import com.livk.cloud.api.domain.route.Predicate;
import com.livk.common.core.converter.BaseConverter;
import com.livk.common.core.util.JacksonUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * DynamicRouteConverter
 * </p>
 *
 * @author livk
 * @date 2021/11/4
 */
@Mapper(imports = { JacksonUtil.class, Predicate.class, Filter.class })
public interface DynamicRouteConverter extends BaseConverter<RedisRoute, DynamicRoute> {

	DynamicRouteConverter INSTANCE = Mappers.getMapper(DynamicRouteConverter.class);

	@Mapping(target = "predicates",
			expression = "java(JacksonUtil.toStream(dynamicRoute.getPredicates(),Predicate.class).toList())")
	@Mapping(target = "filters",
			expression = "java(JacksonUtil.toStream(dynamicRoute.getFilters(),Filter.class).toList())")
	@Mapping(target = "metadata",
			expression = "java(JacksonUtil.toMap(dynamicRoute.getMetadata(),String.class,Object.class))")
	@Override
	RedisRoute getSource(DynamicRoute dynamicRoute);

	@Mapping(target = "insertTime", ignore = true)
	@Mapping(target = "updateTime", ignore = true)
	@Mapping(target = "predicates", expression = "java(JacksonUtil.toJson(redisRoute.getPredicates()))")
	@Mapping(target = "filters", expression = "java(JacksonUtil.toJson(redisRoute.getFilters()))")
	@Mapping(target = "metadata", expression = "java(JacksonUtil.toJson(redisRoute.getMetadata()))")
	@Override
	DynamicRoute getTarget(RedisRoute redisRoute);

}
