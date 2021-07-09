package com.kris.admin.route.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kris.admin.route.model.DynamicRoute;

/**
 * The interface Dynamic route service.
 *
 * @Author: kris
 * @Date: 2021 /7/6
 * @Description: $ {Description}
 * @Since: JDK11
 */
public interface DynamicRouteService extends IService<DynamicRoute> {

  /**
   * 保存路由，并发送
   *
   * @param dynamicRoute 路由实体
   * @param refresh      是否刷新
   * @return 处理结果 boolean
   */
  boolean saveAndSend(DynamicRoute dynamicRoute, boolean refresh);

  /**
   * 更新路由，并发送
   *
   * @param dynamicRoute 路由实体
   * @param refresh      是否刷新
   * @return 处理结果 boolean
   */
  boolean updateByIdAndSend(DynamicRoute dynamicRoute, boolean refresh);

  /**
   * 删除路由，并发送
   *
   * @param id      路由id
   * @param refresh 是否刷新
   * @return 处理结果 boolean
   */
  boolean removeByIdAndSend(String id, boolean refresh);

  /**
   * 刷新redis信息和路由
   *
   * @return 处理结果
   */
  boolean refresh();
}
