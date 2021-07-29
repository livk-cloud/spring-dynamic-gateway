package com.kris.common.core.handler;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Author: chris
 * @Date: 2021/7/26
 * @Description:
 * @Since: JDK11
 */
public class KrisRequestContextHolder {

  private KrisRequestContextHolder() {
  }

  public static HttpServletRequest getRequest() {
    var requestAttributes = RequestContextHolder.getRequestAttributes();
    var servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
    assert servletRequestAttributes != null;
    return servletRequestAttributes.getRequest();
  }
}
