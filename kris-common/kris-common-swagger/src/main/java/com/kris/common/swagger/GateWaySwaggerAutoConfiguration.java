package com.kris.common.swagger;

import com.kris.common.swagger.config.WebFluxSwaggerConfig;
import com.kris.common.swagger.gateway.GatewaySwaggerResourcesProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * @Author: kris
 * @Date: 2021/7/15
 * @Description:
 * @Since: JDK11
 */
@ConditionalOnWebApplication(type = Type.REACTIVE)
public class GateWaySwaggerAutoConfiguration {

  @Bean
  public GatewaySwaggerResourcesProvider gatewaySwaggerResourcesProvider(
      RouteLocator routeLocator, Environment environment,
      RouteDefinitionLocator routeDefinitionLocator) {
    return new GatewaySwaggerResourcesProvider(routeLocator, environment, routeDefinitionLocator);
  }

  @Bean
  public WebFluxSwaggerConfig webFluxSwaggerConfig() {
    return new WebFluxSwaggerConfig();
  }
}
