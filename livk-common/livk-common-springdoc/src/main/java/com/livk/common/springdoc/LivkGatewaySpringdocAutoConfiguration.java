package com.livk.common.springdoc;

import com.livk.common.springdoc.config.WebFluxSpringConfig;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.*;

/**
 * <p>
 * LivkGatewayAutoConfiguration
 * </p>
 *
 * @author livk
 * @date 2022/6/10
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(GatewayAutoConfiguration.class)
@ConditionalOnBean(RouteDefinitionRepository.class)
public class LivkGatewaySpringdocAutoConfiguration {

    @Bean
    @Lazy(false)
    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters,
                                     RouteDefinitionRepository routeDefinitionRepository, DiscoveryClient discoveryClient) {
        var routeDefinitions = new HashSet<RouteDefinition>();
        routeDefinitionRepository.getRouteDefinitions()
                .filter(routeDefinition -> discoveryClient.getServices().contains(routeDefinition.getId()))
                .sort(Comparator.comparingInt(RouteDefinition::getOrder)).subscribe(routeDefinitions::add);
        return routeDefinitions.stream().flatMap(routeDefinition -> routeDefinition.getPredicates().stream()
                .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                .filter(predicateDefinition -> !predicateDefinition.getArgs().isEmpty()).map(predicateDefinition -> {
                    Map<String, String> args = predicateDefinition.getArgs();
                    var pattern = Optional.ofNullable(args.get("pattern"))
                            .orElse(args.get(NameUtils.GENERATED_NAME_PREFIX + "0"));
                    String name = pattern.replace("/**", "");
                    swaggerUiConfigParameters.addGroup(name);
                    return GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
                })).toList();
    }

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(new Info().title("springdoc gateway API").description("springdoc gateway API")
                .version("v1.0.0").contact(new Contact().name("livk").email("livk@163.com")));
    }

    @Bean
    public WebFluxConfigurer webFluxConfigurer() {
        return new WebFluxSpringConfig();
    }

}
