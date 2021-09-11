package com.kris.common.core.spring;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
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
    private static final String ADD = "\\u0049,\\u0050,\\u0020,\\u0041,\\u0044,\\u0044,\\u0052,\\uff1a\\u9\\u68,\\u74,\\u74,\\u70,\\u3a,\\u2f,\\u2f,\\u7b,\\u7d,\\u3a,\\u7b,\\u7d";

    private static final String ADD_UTF8;

    private LivkSpring() {
    }

    static {
        ADD_UTF8 = Arrays.stream(ADD.split(",")).map(LivkSpring::unicodeToStr).reduce(String::concat).orElse("");
    }

    @SneakyThrows
    public static <T> void run(Class<T> targetClass, String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(targetClass)
                .banner(LivkBanner.create())
                .bannerMode(Banner.Mode.CONSOLE)
                .run(args);
        Environment environment = context.getEnvironment();
        log.info(ADD_UTF8, InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port") == null ? "8080" : environment.getProperty("server.port"));
    }

    private static String unicodeToStr(String unicode) {
        StringBuilder builder = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (String str : hex) {
            if (str == null || "".equals(str)) {
                continue;
            }
            builder.append((char) Integer.parseInt(str, 16));
        }
        return builder.toString();
    }
}
