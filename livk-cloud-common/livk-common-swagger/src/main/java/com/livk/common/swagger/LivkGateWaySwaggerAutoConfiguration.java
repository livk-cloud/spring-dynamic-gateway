package com.livk.common.swagger;

import com.livk.common.swagger.config.SwaggerProperties;
import com.livk.common.swagger.config.WebFluxSwaggerConfig;
import com.livk.common.swagger.support.GatewaySwaggerResourcesProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * <p>
 * GateWaySwaggerAutoConfiguration
 * </p>
 *
 * @author livk
 * @date 2021/11/9
 */
@EnableConfigurationProperties(SwaggerProperties.class)
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(GatewayAutoConfiguration.class)
public class LivkGateWaySwaggerAutoConfiguration {

    @Bean
    @Primary
    public GatewaySwaggerResourcesProvider gatewaySwaggerResourcesProvider(SwaggerProperties swaggerProperties,
                                                                           DiscoveryClient discoveryClient, RouteDefinitionRepository routeDefinitionRepository) {
        return new GatewaySwaggerResourcesProvider(routeDefinitionRepository, discoveryClient, swaggerProperties);
    }

    @Bean
    public WebFluxSwaggerConfig webFluxSwaggerConfig() {
        return new WebFluxSwaggerConfig();
    }

}
