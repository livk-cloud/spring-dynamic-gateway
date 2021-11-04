package com.livk.cloud.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.livk.cloud.api.domain.DynamicRoute;
import com.livk.cloud.api.domain.RedisRoute;

/**
 * <p>
 * DynamicRouteService
 * </p>
 *
 * @author livk
 * @date 2021/11/4
 */
public interface DynamicRouteService extends IService<DynamicRoute> {
    Boolean saveOrUpdate(RedisRoute redisRoute);

    Boolean delete(String routeId);
}
