package com.livk.common.log;

import com.livk.common.log.aspect.LogAspect;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * <p>
 * LivkLogAutoConfiguration
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class LivkLogAutoConfiguration {

    @Bean
    public LogAspect logAspect(Environment environment) {
        return new LogAspect(environment);
    }
}
