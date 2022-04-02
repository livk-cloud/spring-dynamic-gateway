package com.livk.common.bus;

import com.livk.common.bus.event.LivkRemoteEvent;
import com.livk.common.bus.handler.LivkRemoteHandler;
import com.livk.common.bus.aspect.RemoteAspect;
import com.livk.common.bus.listener.LivkRemoteListener;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.bus.BusAutoConfiguration;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <p>
 * LivkBusAutoConfiguration
 * </p>
 *
 * @author livk
 * @date 2021/11/22
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(BusAutoConfiguration.class)
@RemoteApplicationEventScan(basePackageClasses = LivkRemoteEvent.class)
public class LivkBusAutoConfiguration {

	@Bean
	public LivkRemoteListener livkRemoteListener(List<LivkRemoteHandler> livkRemoteHandlerList) {
		return new LivkRemoteListener(livkRemoteHandlerList);
	}

	@Bean
	public RemoteAspect remoteAspect(BusProperties busProperties) {
		return new RemoteAspect(busProperties);
	}

}
