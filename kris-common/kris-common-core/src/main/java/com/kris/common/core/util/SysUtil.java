package com.kris.common.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: kris
 * @Date: 2021/7/12
 * @Description:
 * @Since: JDK11
 */
public class SysUtil {

  private static final String UNKNOWN = "unknown";

  private static final String HTTP_IP_SPLIT = ",";

  private SysUtil() {
  }

  public static int getMapSize(int n) {
    if (n <= 0) {
      return -1;
    }
    return n * 4 / 3 + 1;
  }

  public static String getRealIp(HttpServletRequest request) {
    // 这个一般是Nginx反向代理设置的参数
    String ip = request.getHeader("X-Real-IP");
    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Forwarded-For");
    }
    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    // 处理多IP的情况（只取第一个IP）
    return ip != null && ip.contains(HTTP_IP_SPLIT) ? ip.split(HTTP_IP_SPLIT)[0] : ip;
  }
}
