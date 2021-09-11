package com.kris.common.core.util;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * RequestUtil
 *
 * @author livk
 * @date 2021/8/13
 */
public final class RequestUtil {

  private RequestUtil() {}

  public static HttpServletRequest getRequest() {
    var requestAttributes = RequestContextHolder.getRequestAttributes();
    var servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
    assert servletRequestAttributes != null;
    return servletRequestAttributes.getRequest();
  }

  public static HttpSession getSession() {
    return RequestUtil.getRequest().getSession();
  }

  public static String getParameter(String name) {
    return RequestUtil.getRequest().getParameter(name);
  }

  public static String getHeader(String headerName){
    return RequestUtil.getRequest().getHeader(headerName);
  }

  public static Map<String, String> getHeaders() {
    var request = RequestUtil.getRequest();
    var map = new LinkedHashMap<String, String>();
    var enumeration = request.getHeaderNames();
    if (enumeration != null) {
      while (enumeration.hasMoreElements()) {
        var key = enumeration.nextElement();
        var value = request.getHeader(key);
        map.put(key, value);
      }
    }
    return map;
  }
}
