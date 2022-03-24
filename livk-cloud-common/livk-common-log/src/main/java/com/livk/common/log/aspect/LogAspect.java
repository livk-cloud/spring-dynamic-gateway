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
import org.springframework.core.annotation.Order;

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
        var sysLog = new SysLog();
        var request = RequestUtils.getRequest();
        var signature = (MethodSignature) joinPoint.getSignature();
        var methodName = signature.getMethod().getName();
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
            sysLog.setParams(map);
        }
        sysLog.setResult(proceed);
        sysLog.setMethodName(methodName);
        sysLog.setIp(InetAddress.getByName(SysUtils.getRealIp(request)));
        sysLog.setRuntime(end - start);
        SpringContextHolder
                .publishEvent(new LivkLogEvent(sysLog, SpringContextHolder.getProperty("spring.application.name")));
        return proceed;
    }

}
