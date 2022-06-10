package com.livk.common.springdoc;

import com.livk.common.springdoc.config.SpringdocProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * LivkSwaggerAutoConfiguration
 * </p>
 *
 * @author livk
 * @date 2021/11/9
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SpringdocProperties.class)
@ConditionalOnProperty(name = "springdoc.enable", havingValue = "true")
@ConditionalOnMissingClass(value = "org.springframework.cloud.gateway.config.GatewayAutoConfiguration")
public class LivkSpringdocAutoConfiguration {

	@Bean
	public OpenAPI springOpenAPI(SpringdocProperties springdocProperties, ServiceInstance serviceInstance,
			DiscoveryClient discoveryClient) {
		OpenAPI openAPI = new OpenAPI().info(new Info().title(springdocProperties.getTitle()));
		// servers
		List<Server> serverList = new ArrayList<>();
		String path = serviceInstance.getServiceId();
		List<ServiceInstance> gatewayInstances = discoveryClient.getInstances("livk-gateway");
		gatewayInstances.forEach(
				gateway -> serverList.add(new Server().url(gateway.getHost() + ":" + gateway.getPort() + "/" + path)));
		openAPI.servers(serverList);
		return openAPI;
	}

}
