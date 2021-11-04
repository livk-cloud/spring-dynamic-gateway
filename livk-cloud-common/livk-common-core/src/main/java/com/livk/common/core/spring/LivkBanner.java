package com.livk.common.core.spring;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * LivkBanner
 * </p>
 *
 * @author livk
 * @date 2021/9/11
 */
public class LivkBanner implements Banner {
    private LivkBanner() {
    }

    private static final String[] banner = {
            """
                 ██       ██          ██         ██████   ██                       ██
                ░██      ░░          ░██        ██░░░░██ ░██                      ░██
                ░██       ██ ██    ██░██  ██   ██    ░░  ░██  ██████  ██   ██     ░██
                ░██      ░██░██   ░██░██ ██   ░██        ░██ ██░░░░██░██  ░██  ██████
                ░██      ░██░░██ ░██ ░████    ░██        ░██░██   ░██░██  ░██ ██░░░██
                ░██      ░██ ░░████  ░██░██   ░░██    ██ ░██░██   ░██░██  ░██░██  ░██
                ░████████░██  ░░██   ░██░░██   ░░██████  ███░░██████ ░░██████░░██████
                ░░░░░░░░ ░░    ░░    ░░  ░░     ░░░░░░  ░░░  ░░░░░░   ░░░░░░  ░░░░░░\s
                """
    };

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        for (var line : banner) {
            out.println(line);
        }
        var version = SpringBootVersion.getVersion();
        // 当前时间
        out.println(out(22) + "Spring Boot Version:" + version + out(22));
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        out.println(out(18) + "Current time：" + dateFormat.format(new Date()) + out(18));
        out.println(out(23) + "Current JDK Version：" + System.getProperty("java.version") + out(23));
        out.println(out(21) + "Operating System：" + System.getProperty("os.name") + out(20));
        out.flush();
    }

    private String out(int num) {
        return String.valueOf('*').repeat(Math.max(0, num));
    }

    public static LivkBanner create() {
        return new LivkBanner();
    }
}

