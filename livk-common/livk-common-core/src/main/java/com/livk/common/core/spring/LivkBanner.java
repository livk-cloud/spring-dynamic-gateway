package com.livk.common.core.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <p>
 * LivkBanner
 * </p>
 *
 * @author livk
 * @date 2021/9/11
 */
public class LivkBanner implements Banner {

    private static final String[] banner = {"""
			 ██       ██          ██         ██████   ██                       ██
			░██      ░░          ░██        ██░░░░██ ░██                      ░██
			░██       ██ ██    ██░██  ██   ██    ░░  ░██  ██████  ██   ██     ░██
			░██      ░██░██   ░██░██ ██   ░██        ░██ ██░░░░██░██  ░██  ██████
			░██      ░██░░██ ░██ ░████    ░██        ░██░██   ░██░██  ░██ ██░░░██
			░██      ░██ ░░████  ░██░██   ░░██    ██ ░██░██   ░██░██  ░██░██  ░██
			░████████░██  ░░██   ░██░░██   ░░██████  ███░░██████ ░░██████░░██████
			░░░░░░░░ ░░    ░░    ░░  ░░     ░░░░░░  ░░░  ░░░░░░   ░░░░░░  ░░░░░░\s
			"""};

    private LivkBanner() {
    }

    public static LivkBanner create() {
        return new LivkBanner();
    }

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        for (var line : banner) {
            out.println(line);
        }
        var format = Format.create(out);
        format.accept("Spring Boot Version:" + SpringBootVersion.getVersion());
        format.accept("Current time：" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()));
        format.accept("Current JDK Version：" + System.getProperty("java.version"));
        format.accept("Operating System：" + System.getProperty("os.name"));
        out.flush();
    }

    private record Format(int n, PrintStream out, char ch) implements Function<String, String>, Consumer<String> {
        public static Format create(PrintStream out) {
            return new Format(70, out, '*');
        }

        @Override
        public String apply(String str) {
            var length = str.length();
            if (length >= n) {
                return str;
            }
            var index = (n - length) >> 1;
            str = StringUtils.leftPad(str, length + index, ch);
            return StringUtils.rightPad(str, n, ch);
        }

        @Override
        public void accept(String s) {
            out.println(this.apply(s));
        }
    }

}
