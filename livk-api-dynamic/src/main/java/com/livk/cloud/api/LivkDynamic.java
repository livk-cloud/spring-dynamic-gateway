package com.livk.cloud.api;

import com.livk.common.core.spring.LivkSpring;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * LivkDynamic
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@SpringBootApplication
public class LivkDynamic {
    public static void main(String[] args) {
        System.setProperty("spring.cloud.gateway.enabled", "false");
        LivkSpring.run(LivkDynamic.class, args);
    }
}