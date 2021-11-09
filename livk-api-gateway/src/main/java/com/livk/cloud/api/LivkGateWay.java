package com.livk.cloud.api;

import com.livk.common.core.spring.LivkSpring;
import com.livk.common.swagger.annotation.EnableLivkOpenApi;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <p>
 * LivkGateWay
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@EnableLivkOpenApi
@SpringBootApplication
public class LivkGateWay {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = LivkSpring.run(LivkGateWay.class, args);
        System.out.println(context.getBean(DiscoveryClient.class));
        System.out.println(context.getBean(RouteDefinitionRepository.class));
    }
}
