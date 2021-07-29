package com.kris.common.log.aspect;

import com.kris.common.core.handler.KrisRequestContextHolder;
import com.kris.common.core.handler.SpringContextHolder;
import com.kris.common.core.util.SysUtil;
import com.kris.common.log.annotation.KrisLog;
import com.kris.common.log.domain.Log;
import com.kris.common.log.domain.LogEvent;
import java.net.InetAddress;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * @Author: kris
 * @Date: 2021/7/12
 * @Description:
 * @Since: JDK11
 */
@Slf4j
@Aspect
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LogAspect {

  private final Environment environment;

  @Pointcut("@annotation(com.kris.common.log.annotation.KrisLog)||@within(com.kris.common.log.annotation.KrisLog)")
  private void cutPoint() {
  }

  @Around("cutPoint()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    var log = new Log();
    var request = KrisRequestContextHolder.getRequest();
    //获取token，再解析token，获取username
    var username = request.getHeader("xxx");
    var signature = (MethodSignature) joinPoint.getSignature();
    var methodName = signature.getMethod().getName();
    var krisLog = signature.getMethod().getAnnotation(KrisLog.class);
    var parameterNames = signature.getParameterNames();
    var args = joinPoint.getArgs();
    var start = System.currentTimeMillis();
    var proceed = joinPoint.proceed();
    var end = System.currentTimeMillis();
    if (krisLog.isSaveParamAndReturn() && parameterNames.length != 0 && args.length != 0) {
      var map = new HashMap<String, Object>(SysUtil.getMapSize(parameterNames.length));
      for (var i = 0; i < parameterNames.length; i++) {
        map.put(parameterNames[i], args[i]);
      }
      log.setParams(map);
      log.setResult(proceed);
    }
    log.setService(environment.getProperty("spring.application.name"));
    log.setUsername(username);
    log.setMethod(methodName);
    log.setDescription(krisLog.description());
    log.setIp(InetAddress.getByName(SysUtil.getRealIp(request)));
    log.setRuntime(end - start);
    SpringContextHolder.publishEvent(new LogEvent(log));
    return proceed;
  }
}

