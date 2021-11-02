package com.livk.common.redis.annoration;

import org.springframework.cache.annotation.EnableCaching;

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
public @interface EnableLivkCache {
}
