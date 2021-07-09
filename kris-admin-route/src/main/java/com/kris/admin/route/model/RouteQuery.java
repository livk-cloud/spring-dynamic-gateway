package com.kris.admin.route.model;

import lombok.Data;

/**
 * @Author: kris
 * @Date: 2021/7/9
 * @Description:
 * @Since: JDK11
 */
@Data
public class RouteQuery {

  /**
   * 路由id
   */
  private String routeId;
  /**
   * uri路径
   */
  private String uri;
  /**
   * #跳转类型，0时从注册中心获取地址，1直接跳转网络地址
   */
  private Integer routeType;
  /**
   * 加载顺序
   */
  private Integer order;
  /**
   * 状态：1-有效，0-无效
   */
  private Integer status;
}
