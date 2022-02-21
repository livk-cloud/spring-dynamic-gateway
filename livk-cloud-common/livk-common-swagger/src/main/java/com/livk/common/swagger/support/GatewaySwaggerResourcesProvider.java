package com.livk.common.swagger.support;

import com.livk.common.swagger.config.SwaggerProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NameUtils;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.*;

/**
 * <p>
 * GatewaySwaggerResourcesProvider
 * </p>
 *
 * @author livk
 * @date 2021/11/9
 */
@Slf4j
@RequiredArgsConstructor
public class GatewaySwaggerResourcesProvider implements SwaggerResourcesProvider {

	/**
	 * Swagger3默认的url后缀(v3有问题)
	 */
	public static final String SWAGGER2URL = "/v2/api-docs";

	public static final String SWAGGER3URL = "/v3/api-docs";

	private final RouteDefinitionRepository routeDefinitionRepository;

	private final DiscoveryClient discoveryClient;

	private final SwaggerProperties swaggerProperties;

	@Override
	public List<SwaggerResource> get() {
		var routeDefinitions = new HashSet<RouteDefinition>();
		routeDefinitionRepository.getRouteDefinitions()
				.filter(routeDefinition -> discoveryClient.getServices().contains(routeDefinition.getId()))
				.sort(Comparator.comparingInt(RouteDefinition::getOrder)).subscribe(routeDefinitions::add);
		return routeDefinitions.stream().flatMap(routeDefinition -> routeDefinition.getPredicates().stream()
				.filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
				.filter(predicateDefinition -> !swaggerProperties.getIgnoreProviders()
						.contains(routeDefinition.getId()))
				.filter(predicateDefinition -> !predicateDefinition.getArgs().isEmpty()).map(predicateDefinition -> {
					Map<String, String> args = predicateDefinition.getArgs();
					var pattern = Optional.ofNullable(args.get("pattern"))
							.orElse(args.get(NameUtils.GENERATED_NAME_PREFIX + "0"));
					return swaggerResource(routeDefinition.getId(), pattern.replace("/**", SWAGGER2URL));
				})).toList();
	}

	private SwaggerResource swaggerResource(String name, String location) {
		var swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion("2.0");
		return swaggerResource;
	}

}
