package com.livk.cloud.api.converter;

import com.livk.cloud.api.domain.DynamicRoute;
import com.livk.cloud.api.domain.RedisRoute;
import com.livk.common.core.converter.BaseConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * DynamicRouteConverter
 * </p>
 *
 * @author livk
 * @date 2021/11/4
 */
@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface DynamicRouteConverter extends BaseConverter<RedisRoute, DynamicRoute> {
    DynamicRouteConverter INSTANCE = Mappers.getMapper(DynamicRouteConverter.class);

    @Mapping(target = "predicates", expression = "java(com.livk.common.core.util.JacksonUtil.strToCollection(dynamicRoute.getPredicates(),com.livk.cloud.api.domain.route.Predicate.class))")
    @Mapping(target = "filters", expression = "java(com.livk.common.core.util.JacksonUtil.strToCollection(dynamicRoute.getFilters(),com.livk.cloud.api.domain.route.Filter.class))")
    @Mapping(target = "metadata", expression = "java(com.livk.common.core.util.JacksonUtil.strToMap(dynamicRoute.getMetadata(),String.class,Object.class))")
    @Override
    RedisRoute getSource(DynamicRoute dynamicRoute);

    @Mapping(target = "insertTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "predicates", expression = "java(com.livk.common.core.util.JacksonUtil.objToStr(redisRoute.getPredicates()))")
    @Mapping(target = "filters", expression = "java(com.livk.common.core.util.JacksonUtil.objToStr(redisRoute.getFilters()))")
    @Mapping(target = "metadata", expression = "java(com.livk.common.core.util.JacksonUtil.objToStr(redisRoute.getMetadata()))")
    @Override
    DynamicRoute getTarget(RedisRoute redisRoute);
}
