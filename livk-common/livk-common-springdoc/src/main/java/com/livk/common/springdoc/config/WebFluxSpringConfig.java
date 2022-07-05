package com.livk.common.springdoc.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * <p>
 * WebFluxSwaggerConfig
 * </p>
 *
 * @author livk
 * @date 2021/11/9
 */
@ConditionalOnClass(WebFluxConfigurer.class)
public class WebFluxSpringConfig implements WebFluxConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/**").resourceChain(false);
    }

}
