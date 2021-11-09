package com.livk.common.swagger.support;

import com.livk.common.swagger.config.SwaggerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.*;
import java.util.function.Supplier;

/**
 * <p>
 * GatewaySwaggerResourcesProvider
 * </p>
 *
 * @author livk
 * @date 2021/11/9
 */
@Slf4j
@Primary
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GatewaySwaggerResourcesProvider implements SwaggerResourcesProvider {

    /**
     * Swagger3默认的url后缀(v3有问题)
     */
    public static final String SWAGGER3URL = "/v2/api-docs";

    private final RouteDefinitionRepository routeDefinitionRepository;

    private final DiscoveryClient discoveryClient;

    private final SwaggerProperties swaggerProperties;

    @Override
    public List<SwaggerResource> get() {
        Set<RouteDefinition> routeDefinitions = new HashSet<>();
        routeDefinitionRepository.getRouteDefinitions()
                .filter(routeDefinition -> discoveryClient.getServices().contains(routeDefinition.getId()))
                .sort(Comparator.comparingInt(RouteDefinition::getOrder))
                .subscribe(routeDefinitions::add);
        return routeDefinitions.stream().flatMap(routeDefinition -> routeDefinition.getPredicates().stream()
                .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                .filter(predicateDefinition -> !swaggerProperties.getIgnoreProviders().contains(routeDefinition.getId()))
                .filter(predicateDefinition -> !predicateDefinition.getArgs().isEmpty())
                .map(predicateDefinition -> {
                    Map<String, String> args = predicateDefinition.getArgs();
                    String pattern = this.choose(() -> args.get("pattern"), () -> args.get(NameUtils.GENERATED_NAME_PREFIX + "0"));
                    return swaggerResource(routeDefinition.getId(), pattern.replace("/**", SWAGGER3URL));
                })).toList();
    }

    private <T> T choose(Supplier<T> s1, Supplier<T> s2) {
        return Optional.ofNullable(s1.get()).orElse(s2.get());
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
