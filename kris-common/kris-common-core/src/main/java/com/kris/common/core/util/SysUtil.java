package com.kris.common.core.util;

import com.kris.common.core.constant.KrisResultEnum;
import com.kris.common.core.result.R;
import com.kris.common.core.result.R.Constant;
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
    var ip = request.getHeader("X-Real-IP");
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

  /**
   * 包装返回值
   *
   * @param result
   * @return
   */
  public static String packageResult(String result) {
    var map = JacksonUtil.strToMap(result, String.class, Object.class);
    if (map.get(Constant.CODE) != null && map.get(Constant.MSG) != null) {
      return result;
    }
    if (result == null) {
      return JacksonUtil.objToStr(R.error(Constant.ERROR));
    }
    if (!Boolean.parseBoolean(result)) {
      return JacksonUtil.objToStr(R.error(Constant.ERROR, result));
    }
    return JacksonUtil.objToStr(R.result(KrisResultEnum.SUCCESS, result));
  }
}
