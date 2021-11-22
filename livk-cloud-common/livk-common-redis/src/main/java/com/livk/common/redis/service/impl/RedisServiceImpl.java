package com.livk.common.redis.service.impl;

import com.livk.common.redis.service.RedisService;
import com.livk.common.redis.support.LivkRedisTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * RedisServiceImpl
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RedisServiceImpl implements RedisService {

	private final LivkRedisTemplate livkRedisTemplate;

}
