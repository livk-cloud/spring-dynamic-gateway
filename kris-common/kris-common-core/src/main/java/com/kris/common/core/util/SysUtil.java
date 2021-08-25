package com.kris.common.core.util;

import com.kris.common.core.constant.KrisResultEnum;
import com.kris.common.core.result.R;
import com.kris.common.core.result.R.Constant;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

/** @Author: kris @Date: 2021/7/12 @Description: @Since: JDK11 */
public class SysUtil {

  private static final String UNKNOWN = "unknown";

  private static final String HTTP_IP_SPLIT = ",";

  private SysUtil() {}

  /**
   * 获取map的初始化大小
   *
   * @param n 预期添加map数量
   * @return map的初始化大小
   */
  public static int getMapSize(int n) {
    if (n <= 0) {
      return -1;
    }
    return n * 4 / 3 + 1;
  }

  /**
   * 根据request获取IP
   *
   * @param request 请求参数
   * @return ip
   */
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
   * 是否包装返回值 包装返回值
   *
   * @param result 返回值
   * @return 包装后的返回值
   */
  public static String packageResult(String result) {
    if (result == null || "".equals(result)) {
      return JacksonUtil.objToStr(R.ok(Constant.SUCCESS));
    }
    if (checkBool(result)) {
      var parseBoolean = Boolean.parseBoolean(result);
      return JacksonUtil.objToStr(parseBoolean ? R.ok(Constant.SUCCESS) : R.error(Constant.ERROR));
    }
    var map = JacksonUtil.strToMap(result, String.class, Object.class);
    if (checkMap(map)) {
      return result;
    }
    return JacksonUtil.objToStr(R.result(KrisResultEnum.SUCCESS, result));
  }

  /**
   * 检查字符串是否属于boolean类型
   *
   * @param str 需要校验的字符串
   * @return result
   */
  private static boolean checkBool(String str) {
    return Boolean.TRUE.toString().equalsIgnoreCase(str)
        || Boolean.FALSE.toString().equalsIgnoreCase(str);
  }

  /**
   * 检查map是否需要包装
   *
   * @param map 需检查的map
   * @return result
   */
  private static boolean checkMap(Map<String, Object> map) {
    var resultNum = 2;
    if (map == null) {
      return false;
    }
    if (map.size() < resultNum || map.size() > resultNum + 1) {
      return false;
    }
    return map.containsKey(Constant.CODE) && map.containsKey(Constant.MSG);
  }

  public static void run(Class<?> targetClass, String[] args)
      throws UnknownHostException {
    Logger logger = LoggerFactory.getLogger(targetClass);
    Environment env = SpringApplication.run(targetClass, args).getEnvironment();
    logger.info(
        "地址：\thttp://{}:{}",
        InetAddress.getLocalHost().getHostAddress(),
        env.getProperty("server.port"));
  }
}
