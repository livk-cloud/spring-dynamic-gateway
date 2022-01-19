package com.livk.common.redis;

import com.livk.common.redis.service.RedisService;
import com.livk.common.redis.service.impl.RedisServiceImpl;
import com.livk.common.redis.support.LivkReactiveRedisTemplate;
import com.livk.common.redis.support.LivkRedisTemplate;
import com.livk.common.redis.util.SerializerUtils;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p>
 * LivkRedisAutoConfiguration
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class LivkRedisAutoConfiguration {

    @Bean
    @ConditionalOnBean(ReactiveRedisConnectionFactory.class)
    public LivkReactiveRedisTemplate livkReactiveRedisTemplate(ReactiveRedisConnectionFactory redisConnectionFactory) {
        return new LivkReactiveRedisTemplate(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnBean(RedisConnectionFactory.class)
    public LivkRedisTemplate livkRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new LivkRedisTemplate(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnBean(LivkRedisTemplate.class)
    public RedisService redisService(LivkRedisTemplate redisTemplate) {
        return new RedisServiceImpl(redisTemplate);
    }

    @Bean
    @ConditionalOnBean(RedisConnectionFactory.class)
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        var serializer = SerializerUtils.getJacksonSerializer(Object.class);
        return RedisCacheManager.builder(RedisCacheWriter
                        .nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(RedisCacheConfiguration
                        .defaultCacheConfig()
                        .disableCachingNullValues()
                        .serializeKeysWith(RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(new StringRedisSerializer()))
                        .serializeValuesWith(RedisSerializationContext
                                .SerializationPair
                                .fromSerializer(serializer))).build();
    }

}
