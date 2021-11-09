package com.livk.cloud.api;

import com.livk.common.core.spring.LivkSpring;
import com.livk.common.swagger.annotation.EnableLivkOpenApi;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * LivkDynamic
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@EnableLivkOpenApi
@SpringBootApplication
public class LivkDynamic {
    public static void main(String[] args) {
        LivkSpring.run(LivkDynamic.class, args);
    }
}
