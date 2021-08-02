package com.kris.common.core.util;

import com.kris.common.core.result.R;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

/** @Author: chris @Date: 2021/7/26 @Description: @Since: JDK11 */
public class ResponseUtil {

  private ResponseUtil() {}

  /**
   * 根据response写入返回值
   *
   * @param response response
   * @param message 写入的信息
   */
  public static void out(HttpServletResponse response, String message) {
    R<?> r = R.error(message);
    try (PrintWriter out = response.getWriter()) {
      out.print(JacksonUtil.objToStr(r));
      out.flush();
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }
}
