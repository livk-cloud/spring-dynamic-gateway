package com.livk.common.redis;

import com.livk.common.redis.service.RedisService;
import com.livk.common.redis.service.impl.RedisServiceImpl;
import com.livk.common.redis.support.LivkRedisTemplate;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * <p>
 * LivkRedisAutoConfiguration
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration(exclude = org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration.class)
public class LivkRedisAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public LivkRedisTemplate livkRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new LivkRedisTemplate(redisConnectionFactory);
    }

    @Bean
    public RedisService redisService(LivkRedisTemplate redisTemplate) {
        return new RedisServiceImpl(redisTemplate);
    }
}

