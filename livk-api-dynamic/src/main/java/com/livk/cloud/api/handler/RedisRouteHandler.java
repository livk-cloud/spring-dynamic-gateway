package com.livk.cloud.api.handler;

import com.livk.cloud.api.domain.RedisRoute;
import com.livk.common.bus.annotation.LivkEventPublish;
import com.livk.common.core.util.JacksonUtil;
import com.livk.common.redis.support.LivkRedisTemplate;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RedisRouteHandler {

	static final String ROUTE_KEY = "route_key";

	static final String STREAM_BUS_EVENT = "livk-api-gateway:9852:**";

	private final LivkRedisTemplate livkRedisTemplate;

	@LivkEventPublish(STREAM_BUS_EVENT)
	public void reload(List<RedisRoute> redisRouteList) {
		livkRedisTemplate.delete(ROUTE_KEY);
		var redisRouteMap = redisRouteList.stream()
				.collect(Collectors.toMap(RedisRoute::getId, Function.identity()));
		livkRedisTemplate.opsForHash().putAll(ROUTE_KEY, redisRouteMap);
	}

	@LivkEventPublish(STREAM_BUS_EVENT)
	public void push(RedisRoute redisRoute) {
		livkRedisTemplate.opsForHash().put(ROUTE_KEY, redisRoute.getId(), redisRoute);
	}

	@LivkEventPublish(STREAM_BUS_EVENT)
	public void delete(String id) {
		livkRedisTemplate.opsForHash().delete(ROUTE_KEY, id);
	}

	public List<RedisRoute> list() {
		return livkRedisTemplate.opsForHash().entries(ROUTE_KEY).values().stream().map(JacksonUtil::toJson)
				.map(str -> JacksonUtil.toBean(str, RedisRoute.class)).toList();
	}

	public RedisRoute getByRouteId(String routeId) {
		return Optional.ofNullable(livkRedisTemplate.opsForHash().get(ROUTE_KEY, routeId)).map(JacksonUtil::toJson)
				.map(str -> JacksonUtil.toBean(str, RedisRoute.class)).orElse(new RedisRoute());
	}

}
