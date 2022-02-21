package com.livk.common.core.spring;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.InetAddress;
import java.util.Optional;

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

	public <T> ConfigurableApplicationContext runServlet(Class<T> targetClass, String[] args) {
		return LivkSpring.run(targetClass, args, WebApplicationType.SERVLET);
	}

	public <T> ConfigurableApplicationContext runReactive(Class<T> targetClass, String[] args) {
		return LivkSpring.run(targetClass, args, WebApplicationType.REACTIVE);
	}

	@SneakyThrows
	private <T> ConfigurableApplicationContext run(Class<T> targetClass, String[] args,
			WebApplicationType webApplicationType) {
		var context = new SpringApplicationBuilder(targetClass).web(webApplicationType).banner(LivkBanner.create())
				.bannerMode(Banner.Mode.CONSOLE).run(args);
		new Thread(() -> print(context), InetAddress.getLocalHost().getHostAddress()).start();
		return context;
	}

	@SneakyThrows
	private void print(ConfigurableApplicationContext context) {
		var port = context.getEnvironment().getProperty("server.port");
		log.info(HTTP.concat("://{}:{}"), InetAddress.getLocalHost().getHostAddress(),
				Optional.ofNullable(port).orElse("8080"));
	}

}
