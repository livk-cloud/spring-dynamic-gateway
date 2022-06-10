package com.livk.common.log;

import com.livk.common.log.aspect.LogAspect;
import com.livk.common.log.event.LivkLogEventListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * LivkLogAutoConfiguration
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class LivkLogAutoConfiguration {

	@Bean
	public LogAspect logAspect() {
		return new LogAspect();
	}

	@Bean
	public LivkLogEventListener livkLogEventListener() {
		return new LivkLogEventListener();
	}

}
