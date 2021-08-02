package com.kris.common.swagger.gateway;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * @Author: kris
 * @Date: 2021/7/15
 * @Description:
 * @Since: JDK11
 */
@Slf4j
@Primary
@RequiredArgsConstructor(onConstructor_ = {@Autowired, @Lazy})
public class GatewaySwaggerResourcesProvider implements SwaggerResourcesProvider {

  /**
   * Swagger3默认的url后缀(v3有问题)
   */
  public static final String SWAGGER3URL = "/v2/api-docs";

  /** 网关路由 */
  @Value("${spring.application.name}")
  public String appName;

  public static final String DISCOVERY_PREFIX = "ReactiveCompositeDiscoveryClient_";

  private final RouteLocator routeLocator;

  private final Environment environment;

  private final RouteDefinitionLocator routeDefinitionLocator;

  @Override
  public List<SwaggerResource> get() {
    String prefix = environment.getProperty("spring.cloud.gateway.discovery.locator.routeIdPrefix");
    List<SwaggerResource> resourceList = new ArrayList<>();
    List<String> discoveryRoutes = new ArrayList<>();
    List<String> otherRoutes = new ArrayList<>();
    this.routeLocator.getRoutes().subscribe(route -> {
      if (route.getId().startsWith(DISCOVERY_PREFIX) || route.getId()
          .startsWith(Objects.requireNonNull(prefix))) {
        discoveryRoutes.add(route.getUri().getHost());
      } else {
        otherRoutes.add(route.getId());
      }
    });
    this.routeDefinitionLocator.getRouteDefinitions()
        .filter(routeDefinition -> otherRoutes.contains(routeDefinition.getId()))
        .subscribe(routeDefinition -> {
          log.info("Swagger Provider is init");
          List<PredicateDefinition> predicateDefinitions = routeDefinition.getPredicates().stream()
              .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
              .collect(Collectors.toList());
          for (PredicateDefinition definition : predicateDefinitions) {
            String id = routeDefinition.getId();
            Map<String, String> args = definition.getArgs();
            String s = args.get("pattern");
            if (ObjectUtils.isEmpty(s)) {
              s = args.get(NameUtils.GENERATED_NAME_PREFIX + "0");
            }
            s = s.replace("/**", SWAGGER3URL);
            if (ObjectUtils.isEmpty(s)) {
              s = id + SWAGGER3URL;
            }
            resourceList.add(swaggerResource(id, s));
          }
        });
    Set<String> isUse = new HashSet<>();
    log.info("dis Swagger Provider is init");
    discoveryRoutes.forEach(instance -> {
      // 拼接url
      String url = "/" + instance.toLowerCase() + SWAGGER3URL;
      if (!isUse.contains(url) && !appName.equalsIgnoreCase(instance)) {
        isUse.add(url);
        resourceList.add(swaggerResource(instance, url));
      }
    });
    return resourceList;
  }

  private SwaggerResource swaggerResource(String name, String location) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion("2.0");
    return swaggerResource;
  }
}
