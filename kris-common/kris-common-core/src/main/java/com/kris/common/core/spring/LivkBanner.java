package com.kris.common.core.spring;

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
                      " ██       ██          ██         ██████   ██                       ██\n"
                    + "░██      ░░          ░██        ██░░░░██ ░██                      ░██\n"
                    + "░██       ██ ██    ██░██  ██   ██    ░░  ░██  ██████  ██   ██     ░██\n"
                    + "░██      ░██░██   ░██░██ ██   ░██        ░██ ██░░░░██░██  ░██  ██████\n"
                    + "░██      ░██░░██ ░██ ░████    ░██        ░██░██   ░██░██  ░██ ██░░░██\n"
                    + "░██      ░██ ░░████  ░██░██   ░░██    ██ ░██░██   ░██░██  ░██░██  ░██\n"
                    + "░████████░██  ░░██   ░██░░██   ░░██████  ███░░██████ ░░██████░░██████\n"
                    + "░░░░░░░░ ░░    ░░    ░░  ░░     ░░░░░░  ░░░  ░░░░░░   ░░░░░░  ░░░░░░ \n"
    };

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        for (var line : banner) {
            out.println(line);
        }
        var version = SpringBootVersion.getVersion();
        // 当前时间
        out.println("-----------------------Spring Boot Version:" + version + "----------------------");
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        out.println("--------------------Current time：" + dateFormat.format(new Date()) + "------------------");
        out.println("---------------------Current JDK Version：" + System.getProperty("java.version") + "--------------------");
        out.println("----------------------Operating System：" + System.getProperty("os.name") + "---------------------");
        out.flush();
    }

    public static LivkBanner create() {
        return new LivkBanner();
    }
}
