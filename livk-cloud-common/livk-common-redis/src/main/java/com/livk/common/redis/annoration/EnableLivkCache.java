package com.livk.common.redis.annoration;

import com.livk.common.redis.cache.RedisCacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * EnableLivkCache
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableCaching
@Import(RedisCacheConfig.class)
public @interface EnableLivkCache {

}
