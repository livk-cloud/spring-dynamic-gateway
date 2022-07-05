package com.livk.cloud.dynamic.converter;

import com.livk.cloud.dynamic.domain.DynamicRoute;
import com.livk.cloud.dynamic.domain.RedisRoute;
import com.livk.cloud.dynamic.domain.route.Filter;
import com.livk.cloud.dynamic.domain.route.Predicate;
import com.livk.common.core.util.JacksonUtils;
import com.livk.common.mapstruct.converter.Converter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * <p>
 * DynamicRouteConverter
 * </p>
 *
 * @author livk
 * @date 2021/11/4
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {JacksonUtils.class, Predicate.class, Filter.class})
public interface DynamicRouteConverter extends Converter<RedisRoute, DynamicRoute> {

    @Mapping(target = "predicates",
            expression = "java(JacksonUtils.toStream(dynamicRoute.getPredicates(),Predicate.class).toList())")
    @Mapping(target = "filters",
            expression = "java(JacksonUtils.toStream(dynamicRoute.getFilters(),Filter.class).toList())")
    @Mapping(target = "metadata",
            expression = "java(JacksonUtils.toMap(dynamicRoute.getMetadata(),String.class,Object.class))")
    @Override
    RedisRoute getSource(DynamicRoute dynamicRoute);

    @Mapping(target = "insertTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "predicates", expression = "java(JacksonUtils.toJson(redisRoute.getPredicates()))")
    @Mapping(target = "filters", expression = "java(JacksonUtils.toJson(redisRoute.getFilters()))")
    @Mapping(target = "metadata", expression = "java(JacksonUtils.toJson(redisRoute.getMetadata()))")
    @Override
    DynamicRoute getTarget(RedisRoute redisRoute);

}
