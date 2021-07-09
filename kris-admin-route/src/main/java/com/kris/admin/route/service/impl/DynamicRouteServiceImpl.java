package com.kris.admin.route.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kris.admin.route.mapper.DynamicRouteMapper;
import com.kris.admin.route.model.DynamicRoute;
import com.kris.admin.route.service.DynamicRouteService;
import com.kris.common.mq.domain.RouteMessage;
import com.kris.common.mq.domain.TypeEnum;
import com.kris.common.mq.events.MessageSender;
import com.kris.common.redis.service.RedisService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: kris
 * @Date: 2021/7/6
 * @Description: ${Description}
 * @Since: JDK11
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DynamicRouteServiceImpl extends
    ServiceImpl<DynamicRouteMapper, DynamicRoute> implements DynamicRouteService {

  private final RedisService redisService;

  private final MessageSender messageSender;

  @Override
  public boolean saveAndSend(DynamicRoute dynamicRoute, boolean refresh) {
    var save = this.save(dynamicRoute);
    var insertOrUpdate = redisService
        .insertOrUpdate(dynamicRoute, DynamicRoute.SUFFIX_ROUTE + dynamicRoute.getRouteId(), save);
    var routeMessage = this
        .getRouteMessage(DynamicRoute.SUFFIX_ROUTE + dynamicRoute.getRouteId(), TypeEnum.INSERT,
            refresh);
    return messageSender.send(routeMessage, insertOrUpdate);
  }

  @Override
  public boolean updateByIdAndSend(DynamicRoute dynamicRoute, boolean refresh) {
    var update = this.updateById(dynamicRoute);
    var insertOrUpdate = redisService
        .insertOrUpdate(dynamicRoute, DynamicRoute.SUFFIX_ROUTE + dynamicRoute.getRouteId(),
            update);
    var routeMessage = this
        .getRouteMessage(DynamicRoute.SUFFIX_ROUTE + dynamicRoute.getRouteId(), TypeEnum.UPDATE,
            refresh);
    return messageSender.send(routeMessage, insertOrUpdate);
  }

  @Override
  public boolean removeByIdAndSend(String id, boolean refresh) {
    var remove = this.removeById(id);
    var delete = redisService.delete(Collections.singleton(DynamicRoute.SUFFIX_ROUTE + id), remove);
    var routeMessage = this
        .getRouteMessage(DynamicRoute.SUFFIX_ROUTE + id, TypeEnum.UPDATE, refresh);
    return messageSender.send(routeMessage, delete);
  }

  @Override
  public boolean refresh() {
    var keys = redisService.keys(DynamicRoute.SUFFIX_ROUTE);
    redisService.delete(keys);
    var dynamicRouteList = this.list();
    dynamicRouteList.forEach(route -> {
      redisService.insertOrUpdate(route, DynamicRoute.SUFFIX_ROUTE + route.getRouteId(), true);
      var routeMessage = this
          .getRouteMessage(DynamicRoute.SUFFIX_ROUTE + route.getRouteId(), TypeEnum.UPDATE, true);
      messageSender.send(routeMessage, true);
    });
    return true;
  }

  private RouteMessage getRouteMessage(String routingKey, TypeEnum type, Boolean routeIsRefresh) {
    return new RouteMessage(routingKey, type, routeIsRefresh);
  }
}
