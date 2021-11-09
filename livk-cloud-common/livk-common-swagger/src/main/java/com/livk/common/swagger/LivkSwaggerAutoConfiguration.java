package com.livk.common.swagger;

import com.livk.common.swagger.config.SwaggerProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>
 * LivkSwaggerAutoConfiguration
 * </p>
 *
 * @author livk
 * @date 2021/11/9
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "swagger.enable", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class LivkSwaggerAutoConfiguration {

    /**
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
    private static final String ERROR_PATH = "/error";

    private static final String ACTUATOR_PATH = "/actuator/**";

    @Bean
    public Docket createRestApi(SwaggerProperties swaggerProperties) {
        var apis =
                new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo(swaggerProperties))
                        .select()
                        .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()));
        var excludePath = new ArrayList<>(swaggerProperties.getExcludePath());
        excludePath.addAll(Arrays.asList(ERROR_PATH, ACTUATOR_PATH));
        apis.paths(PathSelectors.any());
        excludePath.forEach(path -> apis.paths(PathSelectors.ant(path).negate()));
        return apis.build().pathMapping("/");
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .contact(
                        new Contact(
                                swaggerProperties.getContact().getName(),
                                swaggerProperties.getContact().getUrl(),
                                swaggerProperties.getContact().getEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }
}
