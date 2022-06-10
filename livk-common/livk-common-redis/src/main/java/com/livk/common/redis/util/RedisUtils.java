package com.livk.common.redis.util;

import com.google.common.collect.Lists;
import com.livk.common.core.support.SpringContextHolder;
import com.livk.common.redis.support.LivkRedisTemplate;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * RedisUtils
 * </p>
 *
 * @author livk
 * @date 2022/2/8
 */
@Slf4j
@UtilityClass
public class RedisUtils {

	private static final LivkRedisTemplate TEMPLATE;

	private static final ValueOperations<String, Object> VALUE;

	static {
		TEMPLATE = SpringContextHolder.getBean(LivkRedisTemplate.class);
		VALUE = TEMPLATE.opsForValue();
	}

	/**
	 * 指定缓存失效时间
	 * @param key 键
	 * @param time 时间(秒)
	 * @param timeUnit 单位
	 */
	public boolean expire(String key, long time, TimeUnit timeUnit) {
		try {
			if (time > 0) {
				TEMPLATE.expire(key, time, timeUnit);
			}
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/**
	 * 指定缓存失效时间
	 * @param key 键
	 * @param time 时间(秒)
	 */
	public boolean expire(String key, long time) {
		return expire(key, time, TimeUnit.SECONDS);
	}

	/**
	 * 根据 key 获取过期时间
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public Long getExpire(String key) {
		return TEMPLATE.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 查找匹配key
	 * @param pattern key
	 * @return /
	 */
	public List<String> scanKey(String pattern) {
		return findKeysPage(pattern, -1, -1);
	}

	/**
	 * 分页查询 key
	 * @param patternKey key
	 * @param page 页码
	 * @param size 每页数目
	 * @return /
	 */
	public List<String> findKeysPage(String patternKey, int page, int size) {
		var options = ScanOptions.scanOptions().match(patternKey).build();
		var factory = TEMPLATE.getConnectionFactory();
		var connection = Objects.requireNonNull(factory).getConnection();
		var cursor = connection.scan(options);
		var resultStream = Lists.newArrayList(cursor).stream().map(String::new);
		if (page != -1 && size != -1) {
			resultStream = resultStream.skip((long) page * size).limit(size);
		}
		try {
			RedisConnectionUtils.releaseConnection(connection, factory);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return resultStream.toList();
	}

	/**
	 * 判断key是否存在
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public Boolean hasKey(String key) {
		return TEMPLATE.hasKey(key);
	}

	/**
	 * 删除缓存
	 * @param keys 可以传一个值 或多个
	 */
	public Long del(String... keys) {
		if (keys != null && keys.length > 0) {
			var keySet = Arrays.stream(keys).collect(Collectors.toSet());
			return TEMPLATE.delete(keySet);
		}
		return 0L;
	}

	/**
	 * 普通缓存获取
	 * @param key 键
	 * @return 值
	 */
	public Object get(String key) {
		return key == null ? null : VALUE.get(key);
	}

	/**
	 * 批量获取
	 * @param keys
	 * @return list
	 */
	public List<Object> multiGet(List<String> keys) {
		return VALUE.multiGet(keys);
	}

}
