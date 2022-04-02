package com.livk.common.log.aspect;

import com.livk.common.core.support.SpringContextHolder;
import com.livk.common.core.util.RequestUtils;
import com.livk.common.core.util.SysUtils;
import com.livk.common.log.annotation.LivkLog;
import com.livk.common.log.event.LivkLogEvent;
import com.livk.sys.entity.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;

import java.net.InetAddress;
import java.util.HashMap;

/**
 * <p>
 * LogAspect
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Slf4j
@Aspect
@Order(-1)
public class LogAspect {

    @Around("@annotation(livkLog)||@within(livkLog)")
    public Object around(ProceedingJoinPoint joinPoint, LivkLog livkLog) throws Throwable {
        var signature = (MethodSignature) joinPoint.getSignature();
        var method = signature.getMethod();
        if (livkLog == null) {
            livkLog = AnnotationUtils.findAnnotation(method, LivkLog.class);
            Assert.notNull(livkLog, "LivkLog is null");
        }
        var builder = SysLog.builder();
        var parameterNames = signature.getParameterNames();
        var args = joinPoint.getArgs();
        var start = System.currentTimeMillis();
        var proceed = joinPoint.proceed();
        var end = System.currentTimeMillis();
        if (parameterNames.length != 0 && args.length != 0) {
            var map = new HashMap<String, Object>(SysUtils.getMapSize(parameterNames.length));
            for (var i = 0; i < parameterNames.length; i++) {
                map.put(parameterNames[i], args[i]);
            }
            builder.params(map);
        }
        builder.result(proceed);
        builder.methodName(method.getName());
        builder.ip(InetAddress.getByName(SysUtils.getRealIp(RequestUtils.getRequest())));
        builder.runtime(end - start);
        SpringContextHolder.publishEvent(new LivkLogEvent(builder.build(),
                SpringContextHolder.getProperty("spring.application.name")));
        return proceed;
    }

}
