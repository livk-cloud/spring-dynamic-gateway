package com.livk.common.gateway.support;

import com.livk.common.redis.support.LivkRedisTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

/**
 * <p>
 * RedisRouteHealthIndicator
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@Slf4j
@RequiredArgsConstructor
public class RedisRouteHealthIndicator extends AbstractHealthIndicator {

	private final LivkRedisTemplate livkRedisTemplate;

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		if (Boolean.TRUE.equals(livkRedisTemplate.hasKey(RedisRouteDefinitionWriter.ROUTE_KEY))) {
			builder.up();
		}
		else {
			log.warn("Redis路由信息丢失！");
			builder.down();
		}
	}

}
