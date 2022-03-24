package com.livk.common.core.support;

import org.burningwave.core.assembler.StaticComponentContainer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import javax.annotation.Nonnull;

/**
 * <p>
 * CglibApplicationContextInitializer
 * </p>
 *
 * @author livk
 * @date 2022/1/24
 */
@Configuration
public class CglibApplicationContextInitializer
		implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(@Nonnull ConfigurableApplicationContext applicationContext) {
		StaticComponentContainer.Modules.exportAllToAll();
	}

}
