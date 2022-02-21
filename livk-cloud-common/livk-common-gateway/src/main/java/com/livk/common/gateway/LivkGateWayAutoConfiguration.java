package com.livk.common.gateway;

import com.livk.common.bus.LivkBusAutoConfiguration;
import com.livk.common.bus.listener.LivkRemoteListener;
import com.livk.common.gateway.config.LivkRouteProperties;
import com.livk.common.gateway.support.LivkRedisRouteDefinitionRepository;
import com.livk.common.gateway.support.RedisRouteHealthIndicator;
import com.livk.common.gateway.support.RouteHandler;
import com.livk.common.redis.LivkRedisAutoConfiguration;
import com.livk.common.redis.support.LivkReactiveRedisTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.route.RedisRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

/**
 * <p>
 * LivkGateWayConfiguration
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@EnableConfigurationProperties(LivkRouteProperties.class)
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({ LivkRedisAutoConfiguration.class, LivkBusAutoConfiguration.class })
@AutoConfigureBefore(GatewayAutoConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class LivkGateWayAutoConfiguration {

	@Bean
	@ConditionalOnProperty(prefix = "livk.gateway.route", name = "type", havingValue = "Redis_Hash")
	public LivkRedisRouteDefinitionRepository redisRouteDefinitionWriter(
			LivkReactiveRedisTemplate livkReactiveRedisTemplate) {
		return new LivkRedisRouteDefinitionRepository(livkReactiveRedisTemplate);
	}

	@Bean
	@ConditionalOnProperty(prefix = "livk.gateway.route", name = "type", havingValue = "REDIS_STR")
	public RouteDefinitionRepository redisRouteDefinitionRepository(
			ReactiveRedisTemplate<String, RouteDefinition> reactiveRedisTemplate) {
		return new RedisRouteDefinitionRepository(reactiveRedisTemplate);
	}

	@Bean
	@ConditionalOnBean(LivkReactiveRedisTemplate.class)
	public RedisRouteHealthIndicator redisRouteHealthIndicator(LivkReactiveRedisTemplate livkReactiveRedisTemplate) {
		return new RedisRouteHealthIndicator(livkReactiveRedisTemplate);
	}

	@Bean
	@ConditionalOnBean(LivkRemoteListener.class)
	public RouteHandler routeHandler() {
		return new RouteHandler();
	}

}
