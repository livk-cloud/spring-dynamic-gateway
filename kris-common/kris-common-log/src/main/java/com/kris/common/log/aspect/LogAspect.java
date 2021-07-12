package com.kris.common.log.aspect;

import com.kris.common.core.handler.SpringContextHolder;
import com.kris.common.core.util.SysUtil;
import com.kris.common.log.annotation.KrisLog;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    var builder = LogEvent.builder();
    var requestAttributes = RequestContextHolder.getRequestAttributes();
    var servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
    assert servletRequestAttributes != null;
    var request = servletRequestAttributes.getRequest();
    //获取token，再解析token，获取username
    var username = request.getHeader("token");
    var signature = (MethodSignature) joinPoint.getSignature();
    var methodName = signature.getMethod().getName();
    var log = signature.getMethod().getAnnotation(KrisLog.class);
    var parameterNames = signature.getParameterNames();
    var args = joinPoint.getArgs();
    var start = System.currentTimeMillis();
    var proceed = joinPoint.proceed();
    var end = System.currentTimeMillis();
    if (log.isSaveParamAndReturn() && parameterNames.length != 0 && args.length != 0) {
      var map = new HashMap<String, Object>(SysUtil.getMapSize(parameterNames.length));
      for (var i = 0; i < parameterNames.length; i++) {
        map.put(parameterNames[i], args[i]);
      }
      builder.params(map)
          .result(proceed);
    }
    LogEvent logEvent = builder
        .service(environment.getProperty("spring.application.name"))
        .username(username)
        .method(methodName)
        .description(log.description())
        .ip(InetAddress.getByName(SysUtil.getRealIp(request)))
        .runtime(end - start)
        .build();
    SpringContextHolder.publishEvent(logEvent);
    return proceed;
  }
}

