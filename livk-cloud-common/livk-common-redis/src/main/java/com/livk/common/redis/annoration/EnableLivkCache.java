package com.livk.common.redis.annoration;

import com.livk.common.redis.cache.RedisCacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

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
    @AliasFor(annotation = EnableCaching.class)
    boolean proxyTargetClass() default false;

    @AliasFor(annotation = EnableCaching.class)
    AdviceMode mode() default AdviceMode.PROXY;

    @AliasFor(annotation = EnableCaching.class)
    int order() default 2147483647;
}
