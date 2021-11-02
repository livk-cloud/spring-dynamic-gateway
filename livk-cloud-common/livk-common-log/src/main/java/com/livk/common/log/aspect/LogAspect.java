package com.livk.common.log.aspect;

import com.livk.common.core.support.SpringContextHolder;
import com.livk.common.core.util.RequestUtil;
import com.livk.common.core.util.SysUtil;
import com.livk.common.log.annotation.LivkLog;
import com.livk.common.log.event.LivkLogEvent;
import com.livk.common.log.domain.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

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
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LogAspect {
    private final Environment environment;

    @Around("@annotation(com.livk.common.log.annotation.LivkLog)||@within(com.livk.common.log.annotation.LivkLog)")
    public Object around(ProceedingJoinPoint joinPoint, LivkLog livkLog) throws Throwable {
        var log = new Log();
        var request = RequestUtil.getRequest();
        // 获取token，再解析token，获取username
        var username = request.getHeader("xxx");
        var signature = (MethodSignature) joinPoint.getSignature();
        var methodName = signature.getMethod().getName();
        var parameterNames = signature.getParameterNames();
        var args = joinPoint.getArgs();
        var start = System.currentTimeMillis();
        var proceed = joinPoint.proceed();
        var end = System.currentTimeMillis();
        if (livkLog.isSaveParamAndReturn() && parameterNames.length != 0 && args.length != 0) {
            var map = new HashMap<String, Object>(SysUtil.getMapSize(parameterNames.length));
            for (var i = 0; i < parameterNames.length; i++) {
                map.put(parameterNames[i], args[i]);
            }
            log.setParams(map);
            log.setResult(proceed);
        }
        log.setMethod(methodName);
        log.setIp(InetAddress.getByName(SysUtil.getRealIp(request)));
        log.setRuntime(end - start);
        SpringContextHolder.publishEvent(new LivkLogEvent(log, environment.getProperty("spring.application.name")));
        return proceed;
    }
}