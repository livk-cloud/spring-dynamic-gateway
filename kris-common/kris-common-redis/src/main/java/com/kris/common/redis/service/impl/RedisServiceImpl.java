package com.kris.common.redis.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kris.common.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 12:03
 * @since JDK 11
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    private ValueOperations<String, Object> opsForValue;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    private void redisInit() {
        this.opsForValue = redisTemplate.opsForValue();
    }
}
