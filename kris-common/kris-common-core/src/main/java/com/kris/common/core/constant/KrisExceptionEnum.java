package com.kris.common.core.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 11:36
 * @since JDK 11
 */
@Getter
@RequiredArgsConstructor
public enum KrisExceptionEnum {
  /**
   *
   */
  ROUTE_ID_IS_EXIST(4001, "路由id已经存在！"),
  /**
   *
   */
  ROUTE_DOT_EXIST(4002, "路由信息不存在！");

  private final int code;

  private final String msg;
}
