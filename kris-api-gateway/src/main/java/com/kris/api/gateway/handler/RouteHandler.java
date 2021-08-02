package com.kris.api.gateway.handler;

import com.kris.api.gateway.domain.DynamicRoute;
import com.kris.common.core.handler.SpringContextHolder;
import com.kris.common.core.util.JacksonUtil;
import com.kris.common.mq.domain.RouteMessage;
import com.kris.common.redis.service.RedisService;
import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

/** @Author: kris @Date: 2021/7/15 @Description: @Since: JDK11 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RouteHandler {

  private final RouteDefinitionWriter routeDefinitionWriter;

  private final RedisService redisService;

  private static final Integer ROUTE_TYPE = 0;
  private static final String LOAD_LOADBALANCER_SUFFIX = "lb://";

  public void refresh() {
    redisService.listAll(RouteMessage.SUFFIX_ROUTE).stream()
        .map(object -> JacksonUtil.objToBean(object, DynamicRoute.class))
        .filter(Objects::nonNull)
        .map(this::gatewayRouteToRouteDefinition)
        .collect(Collectors.toList())
        .forEach(this::saveRoute);
  }

  public void save(String routeId) {
    DynamicRoute dynamicRoute =
        JacksonUtil.objToBean(
            redisService.getByKey(RouteMessage.SUFFIX_ROUTE + routeId), DynamicRoute.class);
    assert dynamicRoute != null;
    this.saveRoute(this.gatewayRouteToRouteDefinition(dynamicRoute));
  }

  public void update(String routeId) {
    DynamicRoute dynamicRoute =
        JacksonUtil.objToBean(
            redisService.getByKey(RouteMessage.SUFFIX_ROUTE + routeId), DynamicRoute.class);
    assert dynamicRoute != null;
    this.deleteRoute(routeId);
    this.saveRoute(this.gatewayRouteToRouteDefinition(dynamicRoute));
  }

  public void delete(String routeId) {
    this.deleteRoute(routeId);
  }

  private void deleteRoute(String routeId) {
    routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
    SpringContextHolder.publishEvent(new RefreshRoutesEvent(this));
  }

  private void saveRoute(RouteDefinition routeDefinition) {
    routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
    SpringContextHolder.publishEvent(new RefreshRoutesEvent(this));
  }

  private RouteDefinition gatewayRouteToRouteDefinition(DynamicRoute dynamicRoute) {
    RouteDefinition routeDefinition = new RouteDefinition();
    routeDefinition.setId(dynamicRoute.getRouteId());
    routeDefinition.setOrder(dynamicRoute.getOrder());
    URI uri;
    if (ROUTE_TYPE.equals(dynamicRoute.getRouteType())) {
      uri =
          UriComponentsBuilder.fromUriString(LOAD_LOADBALANCER_SUFFIX + dynamicRoute.getUri())
              .build()
              .toUri();
    } else {
      uri = UriComponentsBuilder.fromHttpUrl(dynamicRoute.getUri()).build().toUri();
    }
    routeDefinition.setUri(uri);
    routeDefinition.setFilters(
        new ArrayList<>(
            JacksonUtil.strToCollection(dynamicRoute.getFilters(), FilterDefinition.class)));
    routeDefinition.setPredicates(
        new ArrayList<>(
            JacksonUtil.strToCollection(dynamicRoute.getPredicates(), PredicateDefinition.class)));
    return routeDefinition;
  }
}
