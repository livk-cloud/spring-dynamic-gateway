package com.kris.common.swagger;

import com.kris.common.swagger.config.SwaggerProperties;
import java.util.ArrayList;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 12:08
 * @since JDK 11
 */
@EnableOpenApi
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "swagger.enable", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnMissingClass("org.springframework.cloud.gateway.config.GatewayAutoConfiguration")
public class SwaggerAutoConfigurer {

  /** 默认的排除路径，排除Spring Boot默认的错误处理路径和端点 */
  private static final String ERROR_PATH = "/error";

  private static final String ACTUATOR_PATH = "/actuator/**";

  @Bean
  public Docket createRestApi(SwaggerProperties swaggerProperties) {
    var apis =
        new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo(swaggerProperties))
            .select()
            .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()));
    var excludePath = new ArrayList<String>(swaggerProperties.getExcludePath());
    if (swaggerProperties.getEnableExcludeErrorPath()) {
      excludePath.add(ERROR_PATH);
    }
    if (swaggerProperties.getEnableExcludeActuatorPath()) {
      excludePath.add(ACTUATOR_PATH);
    }
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
