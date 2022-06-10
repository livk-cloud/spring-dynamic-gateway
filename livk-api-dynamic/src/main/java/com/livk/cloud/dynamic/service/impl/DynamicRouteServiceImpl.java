package com.livk.cloud.dynamic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.livk.cloud.dynamic.domain.DynamicRoute;
import com.livk.cloud.dynamic.domain.RedisRoute;
import com.livk.cloud.dynamic.handler.RedisRouteHandler;
import com.livk.cloud.dynamic.mapper.DynamicRouteMapper;
import com.livk.cloud.dynamic.service.DynamicRouteService;
import com.livk.common.mapstruct.utils.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * DynamicRouteServiceImpl
 * </p>
 *
 * @author livk
 * @date 2021/11/4
 */
@Service
@RequiredArgsConstructor
public class DynamicRouteServiceImpl extends ServiceImpl<DynamicRouteMapper, DynamicRoute>
		implements DynamicRouteService {

	private final RedisRouteHandler redisRouteHandler;

	@Override
	public Boolean saveOrUpdate(RedisRoute redisRoute) {
		redisRouteHandler.push(redisRoute);
		var dynamicRoute = MapstructUtils.converter(redisRoute, DynamicRoute.class);
		return this.saveOrUpdate(dynamicRoute);
	}

	@Override
	public Boolean delete(String routeId) {
		redisRouteHandler.delete(routeId);
		return this.removeById(routeId);
	}

	@Override
	public RedisRoute getById(String routeId) {
		return redisRouteHandler.getByRouteId(routeId);
	}

	@Override
	public List<RedisRoute> selectList() {
		return redisRouteHandler.list();
	}

	@Override
	public Boolean reload() {
		var dynamicRouteList = this.list();
		var redisRouteStream = MapstructUtils.converter(dynamicRouteList, RedisRoute.class);
		redisRouteHandler.reload(redisRouteStream.toList());
		return true;
	}

}
