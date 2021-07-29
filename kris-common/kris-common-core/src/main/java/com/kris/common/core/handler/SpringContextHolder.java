package com.kris.common.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Author: kris
 * @Date: 2021/7/12
 * @Description:
 * @Since: JDK11
 */
@Slf4j
@Service
@Lazy(false)
public class SpringContextHolder extends Assert implements ApplicationContextAware, DisposableBean {

  private static ApplicationContext applicationContext = null;

  @Override
  public void setApplicationContext(@Nullable ApplicationContext applicationContext)
      throws BeansException {
    if (SpringContextHolder.applicationContext != null) {
      log.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
          + SpringContextHolder.applicationContext);
    }
    setSpringContext(applicationContext);
  }

  public static ApplicationContext getApplicationContext() {
    check();
    return applicationContext;
  }

  @Override
  public void destroy() throws Exception {
    if (log.isDebugEnabled()) {
      log.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
    }
    setSpringContext(null);
  }

  private synchronized void setSpringContext(ApplicationContext applicationContext) {
    SpringContextHolder.applicationContext = applicationContext;
  }

  /**
   * Spring事件发布
   *
   * @param event 事件
   */
  public static void publishEvent(ApplicationEvent event) {
    check();
    applicationContext.publishEvent(event);
  }

  public static Object getBean(String name) {
    check();
    return applicationContext.getBean(name);
  }

  public static <T> T getBean(Class<T> typeClass) {
    check();
    return applicationContext.getBean(typeClass);
  }

  public static <T> T getBean(String name, Class<T> typeClass) {
    check();
    return applicationContext.getBean(name, typeClass);
  }

  public static void check() {
    notNull(applicationContext, "applicationContext注入失败！");
  }
}
