package com.kris.common.redis.service.impl;

import com.kris.common.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 12:03
 * @since JDK 11
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RedisServiceImpl<T> implements RedisService<T> {

    private final RedisTemplate<String, Object> redisTemplate;

    private ValueOperations<String, Object> opsForValue;

    @PostConstruct
    private void redisInit() {
        this.opsForValue = redisTemplate.opsForValue();
    }

    @Override
    public List<Object> listAll(String prefix) {
        Set<String> keys = this.keys(prefix);
        return keys.stream()
                .map(this::getByKey)
                .collect(Collectors.toList());
    }

    @Override
    public Object getByKey(String key) {
        return opsForValue.get(key);
    }

    @Override
    public boolean insertOrUpdate(Object obj, String key) {
        try {
            opsForValue.set(key, obj);
            return true;
        } catch (Exception e) {
            log.error("Failed to add or modify");
            return false;
        }
    }

    @Override
    public boolean delete(Collection<String> keys, String prefix) {
        try {
            keys = keys.stream().map(key -> prefix + key).collect(Collectors.toList());
            redisTemplate.delete(keys);
            return true;
        } catch (Exception e) {
            log.error("Failed to delete batches！");
            return false;
        }
    }

    @Override
    public Set<String> keys(String prefix) {
        return redisTemplate.keys(prefix + "*");
    }
}
