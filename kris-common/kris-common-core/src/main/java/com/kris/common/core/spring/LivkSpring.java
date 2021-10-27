package com.kris.common.core.spring;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.util.Arrays;

/**
 * <p>
 * LivkSpring
 * </p>
 *
 * @author livk
 * @date 2021/9/11
 */
@Slf4j
public class LivkSpring {
    private static final String http = "IP Address: http";

    private LivkSpring() {
    }

    @SneakyThrows
    public static <T> ConfigurableApplicationContext run(Class<T> targetClass, String[] args) {
        var context = new SpringApplicationBuilder(targetClass)
                .banner(LivkBanner.create())
                .bannerMode(Banner.Mode.CONSOLE)
                .run(args);
        new Thread(() -> print(context), InetAddress.getLocalHost().getHostAddress()).start();
        return context;
    }

    @SneakyThrows
    private static void print(ConfigurableApplicationContext context) {
        var port = context.getEnvironment().getProperty("server.port");
        LivkLog.of(LivkSpring.class).info(http.concat("://{}:{}"),
                InetAddress.getLocalHost().getHostAddress(), port == null ? "8080" : port);
    }
}
