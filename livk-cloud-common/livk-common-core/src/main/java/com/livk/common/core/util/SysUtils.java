package com.livk.common.core.util;

import com.livk.common.core.result.R;
import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * SysUtil
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@UtilityClass
public class SysUtils {

    private static final String UNKNOWN = "unknown";

    private static final String HTTP_IP_SPLIT = ",";

    /**
     * 获取map的初始化大小
     *
     * @param n 预期添加map数量
     * @return map的初始化大小
     */
    public int getMapSize(int n) {
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
    public String getRealIp(HttpServletRequest request) {
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
    public String packageResult(String result) {
        if (result == null || "".equals(result)) {
            return JacksonUtils.toJson(R.ok(R.Constant.SUCCESS));
        }
        if (checkBool(result)) {
            var parseBoolean = Boolean.parseBoolean(result);
            return JacksonUtils.toJson(parseBoolean ? R.ok() : R.error(R.Constant.ERROR));
        }
        if (!(result.startsWith("[") && result.endsWith("]"))) {
            var map = JacksonUtils.toMap(result, String.class, Object.class);
            if (checkMap(map)) {
                return result;
            }
        }
        return JacksonUtils.toJson(R.ok(result));
    }

    /**
     * 检查字符串是否属于boolean类型
     *
     * @param str 需要校验的字符串
     * @return result
     */
    private boolean checkBool(String str) {
        return Boolean.TRUE.toString().equalsIgnoreCase(str) || Boolean.FALSE.toString().equalsIgnoreCase(str);
    }

    /**
     * 检查map是否需要包装
     *
     * @param map 需检查的map
     * @return result
     */
    private boolean checkMap(Map<String, Object> map) {
        var resultNum = 2;
        if (map == null) {
            return false;
        }
        if (map.size() < resultNum || map.size() > resultNum + 1) {
            return false;
        }
        return map.containsKey(R.Constant.CODE) && map.containsKey(R.Constant.MSG);
    }

}
