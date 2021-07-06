package com.kris.common.swagger;

import com.kris.common.swagger.config.SwaggerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 12:08
 * @since JDK 11
 */
@EnableOpenApi
@EnableAutoConfiguration
@ConditionalOnProperty(name = "swagger.enable", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfig {

    /**
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
    private static final String ERROR_PATH = "/error.*";
    private static final String ACTUATOR_PATH = "/actuator/**";


    @Bean
    public Docket createRestApi(SwaggerProperties swaggerProperties) {
        ApiSelectorBuilder apis = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()));
        if (swaggerProperties.getExcludePath().length != 0) {
            for (String path : swaggerProperties.getExcludePath()) {
                apis.paths(Predicate.not(PathSelectors.regex(path)));
            }
        }
        if (swaggerProperties.getEnableExcludeErrorPath()) {
            apis.paths(Predicate.not(PathSelectors.regex(ERROR_PATH)));
        }
        if (swaggerProperties.getEnableExcludeActuatorPath()) {
            apis.paths(Predicate.not(PathSelectors.regex(ACTUATOR_PATH)));
        }
        apis.paths(PathSelectors.any());
        return apis.build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("网关路由管理API")
                .description("网关路由管理")
                .termsOfServiceUrl("https://kris.com")
                .version("2.0")
                .build();
    }
}
