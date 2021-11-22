package com.livk.cloud.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * <p>
 * CorsConfig
 * </p>
 *
 * @author livk
 * @date 2021/11/15
 */
@Configuration
public class CorsConfig {

	@Bean
	public CorsWebFilter corsWebFilter() {
		var source = new UrlBasedCorsConfigurationSource();
		var corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsWebFilter(source);
	}

}
