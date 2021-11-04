package com.livk.common.gateway.support;

import com.livk.common.core.util.JacksonUtil;
import com.livk.common.gateway.domain.LivkRoute;
import com.livk.common.redis.support.LivkRedisTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p>
 * RedisRouteDefinitionWriter
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RedisRouteDefinitionWriter implements RouteDefinitionRepository {

    private final LivkRedisTemplate livkRedisTemplate;

    static final String ROUTE_KEY = "route_key";

    /**
     * 从Redis拿下来是一个LinkedHashMap
     * 使用反射直接操作，uri类型为{@link java.net.URI}
     * 使用JackSon进行序列化和反序列化，将Map转成{@link com.livk.common.gateway.domain.LivkRoute}
     *
     * @return Flux<LivkRoute>
     */
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(livkRedisTemplate.opsForHash().entries(ROUTE_KEY).values()
                .stream().map(JacksonUtil::objToStr).map(str -> JacksonUtil.strToBean(str, LivkRoute.class))
                .toList());
    }

    /**
     * @param route {@link com.livk.common.gateway.domain.LivkRoute}
     * @return Mono.empty()
     */
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            if (ObjectUtils.isEmpty(r.getId())) {
                return Mono.error(new IllegalArgumentException("id may not be empty"));
            }
            livkRedisTemplate.opsForHash().put(ROUTE_KEY, r.getId(), r);
            return Mono.empty();
        });
    }

    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (livkRedisTemplate.opsForHash().hasKey(ROUTE_KEY, id)) {
                livkRedisTemplate.opsForHash().delete(ROUTE_KEY, id);
            } else {
                log.error("RouteDefinition not found: {}", routeId);
            }
            return Mono.empty();
        });
    }
}
