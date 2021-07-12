package com.kris.common.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Lazy;
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
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    if (SpringContextHolder.applicationContext != null) {
      log.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
          + SpringContextHolder.applicationContext);
    }
    SpringContextHolder.applicationContext = applicationContext;
  }

  public static ApplicationContext getApplicationContext() {
    notNull(applicationContext, "applicationContext注入失败！");
    return applicationContext;
  }

  @Override
  public void destroy() throws Exception {
    if (log.isDebugEnabled()) {
      log.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
    }
    SpringContextHolder.applicationContext = null;
  }

  /**
   * Spring事件发布
   *
   * @param event 事件
   */
  public static void publishEvent(ApplicationEvent event) {
    notNull(applicationContext, "applicationContext注入失败！");
    applicationContext.publishEvent(event);
  }

  public static Object getBean(String name) {
    notNull(applicationContext, "applicationContext注入失败！");
    return applicationContext.getBean(name);
  }

  public static <T> T getBean(Class<T> typeClass) {
    notNull(applicationContext, "applicationContext注入失败！");
    return applicationContext.getBean(typeClass);
  }

  public static <T> T getBean(String name, Class<T> typeClass) {
    notNull(applicationContext, "applicationContext注入失败！");
    return applicationContext.getBean(name, typeClass);
  }
}
