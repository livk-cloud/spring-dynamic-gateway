package com.livk.cloud.api.handler;

import com.livk.cloud.api.domain.RedisRoute;
import com.livk.common.bus.annotation.LivkEventPublish;
import com.livk.common.redis.support.LivkRedisTemplate;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * RedisRouteHandler
 * </p>
 *
 * @author livk
 * @date 2021/11/4
 */
@Component
public class RedisRouteHandler {

	static final String ROUTE_KEY = "RouteDefinition";

	/**
	 * spring.application.name:server.port:**
	 */
	static final String LIVK_API_GATEWAY = "livk-api-gateway:9852:**";

	private final LivkRedisTemplate livkRedisTemplate;

	private final HashOperations<String, String, RedisRoute> forHash;

	public RedisRouteHandler(LivkRedisTemplate livkRedisTemplate) {
		this.livkRedisTemplate = livkRedisTemplate;
		this.forHash = livkRedisTemplate.opsForHash();
	}

	@LivkEventPublish(LIVK_API_GATEWAY)
	public void reload(List<RedisRoute> redisRouteList) {
		livkRedisTemplate.delete(ROUTE_KEY);
		var redisRouteMap = redisRouteList.stream().collect(Collectors.toMap(RedisRoute::getId, Function.identity()));
		forHash.putAll(ROUTE_KEY, redisRouteMap);
	}

	@LivkEventPublish(LIVK_API_GATEWAY)
	public void push(RedisRoute redisRoute) {
		forHash.put(ROUTE_KEY, redisRoute.getId(), redisRoute);
	}

	@LivkEventPublish(LIVK_API_GATEWAY)
	public void delete(String id) {
		forHash.delete(ROUTE_KEY, id);
	}

	public List<RedisRoute> list() {
		return forHash.entries(ROUTE_KEY).values().stream().toList();
	}

	public RedisRoute getByRouteId(String routeId) {
		return Optional.ofNullable(forHash.get(ROUTE_KEY, routeId)).orElse(new RedisRoute());
	}

}
