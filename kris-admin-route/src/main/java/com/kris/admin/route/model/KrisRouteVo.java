package com.kris.admin.route.model;

import java.util.List;
import lombok.Data;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

/** @Author: kris @Date: 2021/7/9 @Description: @Since: JDK11 */
@Data
public class KrisRouteVo {

  /** 路由id */
  private String routeId;
  /** uri路径 */
  private String uri;
  /** #跳转类型，0时从注册中心获取地址，1直接跳转网络地址 */
  private Integer routeType;
  /** 判定器 */
  private List<PredicateDefinition> predicates;
  /** 过滤器 */
  private List<FilterDefinition> filters;
  /** 加载顺序 */
  private Integer order;
  /** 描述 */
  private String description;
  /** 状态：1-有效，0-无效 */
  private Integer status;
}
