package com.kris.common.core.result;

import com.kris.common.core.constant.KrisResultEnum;
import com.kris.common.core.exception.KrisException;
import lombok.Getter;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 11:32
 * @since JDK 11
 */
@Getter
public class R<T> {

  /**
   * 返回状态码
   */
  private final int code;
  /**
   * 返回消息
   */
  private final String msg;
  /**
   * 返回数据
   */
  private final T data;

  private R(int code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public static <T> R<T> result(int code, String msg, T data) {
    return new R<>(code, msg, data);
  }

  public static <T> R<T> result(KrisResultEnum krisResultEnum, T data) {
    return result(krisResultEnum.getCode(), krisResultEnum.getMsg(), data);
  }

  public static <T> R<T> ok(String msg, T data) {
    return result(2000, msg, data);
  }

  public static <T> R<T> ok(String msg) {
    return ok(msg, null);
  }

  public static <T> R<T> error(String msg, T data) {
    return result(5001, msg, data);
  }

  public static <T> R<T> error(String msg) {
    return error(msg, null);
  }

  public static <T> R<T> error(KrisException e) {
    return result(e.getCode(), e.getMessage(), null);
  }

  public static class Constant {

    private Constant() {
    }

    public static final String SUCCESS = "success";

    public static final String ERROR = "error";

    public static final String CODE = "code";
    public static final String MSG = "msg";
    public static final String DATA = "data";
  }
}
