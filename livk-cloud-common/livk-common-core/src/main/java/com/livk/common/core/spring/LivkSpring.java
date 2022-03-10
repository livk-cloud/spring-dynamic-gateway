package com.livk.common.core.spring;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.InetAddress;

/**
 * <p>
 * LivkSpring
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Slf4j
@UtilityClass
public class LivkSpring {

	private static final String HTTP = "IP Address: http";

	@SneakyThrows
	public <T> ConfigurableApplicationContext run(Class<T> targetClass, String[] args) {
		var context = new SpringApplicationBuilder(targetClass).banner(LivkBanner.create())
				.bannerMode(Banner.Mode.CONSOLE).run(args);
		new Thread(() -> print(context), InetAddress.getLocalHost().getHostAddress()).start();
		return context;
	}

	@SneakyThrows
	private void print(ConfigurableApplicationContext context) {
		var port = context.getEnvironment().getProperty("server.port", "8080");
		log.info(HTTP.concat("://{}:{}"), InetAddress.getLocalHost().getHostAddress(), port);
	}

}
