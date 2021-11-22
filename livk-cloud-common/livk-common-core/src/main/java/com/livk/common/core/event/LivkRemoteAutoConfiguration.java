package com.livk.common.core.event;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.bus.BusAutoConfiguration;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * LivkRemoteAutoConfiguration
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(BusAutoConfiguration.class)
@AutoConfigureAfter(BusAutoConfiguration.class)
@RemoteApplicationEventScan(basePackageClasses = LivkRemoteApplicationEvent.class)
public class LivkRemoteAutoConfiguration {

	@Bean
	public LivkRemoteListener livkRemoteListener() {
		return new LivkRemoteListener();
	}

}
