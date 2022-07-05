package com.livk.cloud.sys;

import com.livk.common.core.spring.LivkSpring;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * LivkSysApplication
 * </p>
 *
 * @author livk
 * @date 2022/6/9
 */
@SpringBootApplication
public class LivkSysApplication {

    public static void main(String[] args) {
        LivkSpring.run(LivkSysApplication.class, args);
    }

}
