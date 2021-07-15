package com.kris.api.gateway.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: kris
 * @Date: 2021/7/15
 * @Description:
 * @Since: JDK11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DynamicRoute implements Serializable {

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
   * 判定器
   */
  private String predicates;

  /**
   * 过滤器
   */
  private String filters;

  /**
   * 加载顺序
   */
  private Integer order;

  /**
   * 描述
   */
  private String description;

  /**
   * 状态：1-有效，0-无效
   */
  private Integer status;
}

