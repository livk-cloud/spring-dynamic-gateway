package com.livk.cloud.api.handler;

import com.livk.cloud.api.domain.RedisRoute;
import com.livk.common.core.event.LivkRemoteApplicationEvent;
import com.livk.common.core.support.SpringContextHolder;
import com.livk.common.core.util.JacksonUtil;
import com.livk.common.redis.support.LivkRedisTemplate;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    static final String STREAM_BUS_EVENT = "api-gateway:9852:**";

    private final LivkRedisTemplate livkRedisTemplate;

    public String reload(List<RedisRoute> redisRouteList) {
        var keys = livkRedisTemplate.opsForHash().keys(ROUTE_KEY);
        livkRedisTemplate.opsForHash().delete(ROUTE_KEY, keys);
        redisRouteList.forEach(this::push);
        return STREAM_BUS_EVENT;
    }

    public String push(RedisRoute redisRoute) {
        livkRedisTemplate.opsForHash().put(ROUTE_KEY, redisRoute.getId(), redisRoute);
        return STREAM_BUS_EVENT;
    }

    public String delete(String id) {
        livkRedisTemplate.opsForHash().delete(ROUTE_KEY, id);
        return STREAM_BUS_EVENT;
    }

    public List<RedisRoute> list() {
        return livkRedisTemplate.opsForHash().entries(ROUTE_KEY).values()
                .stream().map(JacksonUtil::toJson).map(str -> JacksonUtil.toBean(str, RedisRoute.class))
                .toList();
    }

    public RedisRoute getByRouteId(String routeId) {
        return Optional.ofNullable(livkRedisTemplate.opsForHash().get(ROUTE_KEY, routeId))
                .map(JacksonUtil::toJson).map(str -> JacksonUtil.toBean(str, RedisRoute.class))
                .orElse(new RedisRoute());
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
    @AfterReturning(value = "execution(String com.livk.cloud.api.handler.RedisRouteHandler.*(..))", returning = "destination")
    public void refresh(String destination) {
        SpringContextHolder.publishEvent(new LivkRemoteApplicationEvent(busProperties.getId(), () -> destination));
    }
}
