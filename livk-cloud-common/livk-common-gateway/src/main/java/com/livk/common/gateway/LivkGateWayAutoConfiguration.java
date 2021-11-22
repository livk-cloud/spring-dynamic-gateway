package com.livk.common.gateway;

import com.livk.common.bus.LivkBusAutoConfiguration;
import com.livk.common.bus.listener.LivkRemoteListener;
import com.livk.common.gateway.support.RedisRouteDefinitionWriter;
import com.livk.common.gateway.support.RedisRouteHealthIndicator;
import com.livk.common.gateway.support.RouteHandler;
import com.livk.common.redis.LivkRedisAutoConfiguration;
import com.livk.common.redis.support.LivkRedisTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * LivkGateWayConfiguration
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ LivkRedisAutoConfiguration.class, LivkBusAutoConfiguration.class })
@AutoConfigureBefore(GatewayAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class LivkGateWayAutoConfiguration {

	@Bean
	@ConditionalOnBean(LivkRedisTemplate.class)
	public RedisRouteDefinitionWriter redisRouteDefinitionWriter(LivkRedisTemplate livkRedisTemplate) {
		return new RedisRouteDefinitionWriter(livkRedisTemplate);
	}

	@Bean
	@ConditionalOnBean(LivkRedisTemplate.class)
	public RedisRouteHealthIndicator redisRouteHealthIndicator(LivkRedisTemplate livkRedisTemplate) {
		return new RedisRouteHealthIndicator(livkRedisTemplate);
	}

	@Bean
	@ConditionalOnBean(LivkRemoteListener.class)
	public RouteHandler routeHandler() {
		return new RouteHandler();
	}

}
