package com.livk.cloud.api.handler;

import com.livk.cloud.api.domain.RedisRoute;
import com.livk.common.core.event.LivkRemoteApplicationEvent;
import com.livk.common.core.support.SpringContextHolder;
import com.livk.common.redis.support.LivkRedisTemplate;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * RedisRouteHandler
 * </p>
 *
 * @author livk
 * @date 2021/11/4
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RedisRouteHandler {

    static final String ROUTE_KEY = "route_key";

    private final LivkRedisTemplate livkRedisTemplate;

    public String push(RedisRoute redisRoute) {
        livkRedisTemplate.opsForHash().put(ROUTE_KEY, redisRoute.getId(), redisRoute);
        return "api-gateway:9852:**";
    }

    public String delete(String id) {
        livkRedisTemplate.opsForHash().delete(ROUTE_KEY, id);
        return "api-gateway:9852:**";
    }
}

@Aspect
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class RouteAspect {

    private final BusProperties busProperties;

    /**
     * 需添加配置文件，设置通知那个serviceId
     * 例如"api-gateway:9852:**"
     */
    @AfterReturning(value = "execution(* com.livk.cloud.api.handler.RedisRouteHandler.*(..))", returning = "destination")
    public void after(String destination) {
        SpringContextHolder.publishEvent(new LivkRemoteApplicationEvent(busProperties.getId(), () -> destination));
    }
}
