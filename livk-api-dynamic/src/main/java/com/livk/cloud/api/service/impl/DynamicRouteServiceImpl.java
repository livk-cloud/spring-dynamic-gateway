package com.livk.cloud.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.livk.cloud.api.converter.DynamicRouteConverter;
import com.livk.cloud.api.domain.DynamicRoute;
import com.livk.cloud.api.domain.RedisRoute;
import com.livk.cloud.api.handler.RedisRouteHandler;
import com.livk.cloud.api.mapper.DynamicRouteMapper;
import com.livk.cloud.api.service.DynamicRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DynamicRouteServiceImpl extends ServiceImpl<DynamicRouteMapper, DynamicRoute>
		implements DynamicRouteService {

	private final RedisRouteHandler redisRouteHandler;

	@Override
	public Boolean saveOrUpdate(RedisRoute redisRoute) {
		redisRouteHandler.push(redisRoute);
		var dynamicRoute = DynamicRouteConverter.INSTANCE.getTarget(redisRoute);
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
		var redisRouteStream = DynamicRouteConverter.INSTANCE.streamSource(dynamicRouteList);
		redisRouteHandler.reload(redisRouteStream.toList());
		return true;
	}

}
