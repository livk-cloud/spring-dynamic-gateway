package com.livk.common.redis.aspect;

import com.google.common.collect.ImmutableList;
import com.livk.common.core.util.RequestUtils;
import com.livk.common.core.util.SysUtils;
import com.livk.common.redis.annoration.Limit;
import com.livk.common.redis.constant.LimitType;
import com.livk.common.redis.support.LivkRedisTemplate;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * LimitAspect
 * </p>
 *
 * @author livk
 * @date 2022/2/8
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LimitAspect {

    private final LivkRedisTemplate livkRedisTemplate;

    @Around("@annotation(limit)||@within(limit)")
    public Object around(ProceedingJoinPoint joinPoint, Limit limit) throws Throwable {
        HttpServletRequest request = RequestUtils.getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LimitType limitType = limit.limitType();
        String key = limit.key();
        if (StringUtils.isEmpty(key)) {
            if (limitType == LimitType.IP) {
                key = SysUtils.getRealIp(request);
            } else {
                key = method.getName();
            }
        }
        List<String> keys = ImmutableList.of(StringUtils.join(limit.prefix(), "_", key, "_", request.getRequestURI().replaceAll("/", "_")));

        String luaScript = buildLuaScript();
        RedisScript<Integer> redisScript = new DefaultRedisScript<>(luaScript, Integer.class);
        Integer count = livkRedisTemplate.execute(redisScript, keys, limit.count(), limit.period());
        if (null != count && count <= limit.count()) {
            log.info("第{}次访问key为 {}，描述为 [{}] 的接口", count, keys, limit.name());
            return joinPoint.proceed();
        } else {
            throw new InvalidRequestStateException("访问次数受限制");
        }
    }

    /**
     * 限流脚本
     */
    private String buildLuaScript() {
        return """
                local c
                c = redis.call('get',KEYS[1])
                if c and tonumber(c) > tonumber(ARGV[1]) then
                return c;
                end
                c = redis.call('incr',KEYS[1])
                if tonumber(c) == 1 then
                redis.call('expire',KEYS[1],ARGV[2])
                end
                return c;
                """;
    }
}
