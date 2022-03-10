package com.livk.common.redis.cache;

import com.livk.common.redis.util.SerializerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>
 * RedisCacheConfig
 * </p>
 *
 * @author livk
 * @date 2022/2/28
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class RedisCacheConfig implements CachingConfigurer {

	private final RedisConnectionFactory redisConnectionFactory;

	@Bean
	@Override
	public CacheManager cacheManager() {
		var serializer = SerializerUtils.getJacksonSerializer(Object.class);
		return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
				.cacheDefaults(RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
						.serializeKeysWith(
								RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
						.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer)))
				.build();
	}

	@Bean
	@Override
	public CacheResolver cacheResolver() {
		return new SimpleCacheResolver();
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

	@Override
	public CacheErrorHandler errorHandler() {
		return new SimpleCacheErrorHandler();
	}

}
