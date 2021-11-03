package com.livk.cloud.api.controller;

import com.livk.common.core.event.LivkRemoteApplicationEvent;
import com.livk.common.core.support.SpringContextHolder;
import com.livk.common.gateway.domain.LivkRoute;
import com.livk.common.redis.support.LivkRedisTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * <p>
 * RouteController
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RouteController {

    private final LivkRedisTemplate livkRedisTemplate;

    private final BusProperties busProperties;

    @PostMapping("route")
    public Object addRoute(@RequestBody LivkRoute livkRoute) {
        livkRedisTemplate.opsForHash().put("route_key", livkRoute.getId(), livkRoute);
        SpringContextHolder.publishEvent(new LivkRemoteApplicationEvent(busProperties.getId(), () -> "api-gateway:9852:**"));
        return Mono.empty();
    }
}
