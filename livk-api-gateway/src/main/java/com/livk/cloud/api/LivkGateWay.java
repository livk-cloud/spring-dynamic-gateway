package com.livk.cloud.api;

import com.livk.common.core.spring.LivkSpring;
import com.livk.common.core.support.SpringContextHolder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;

/**
 * <p>
 * LivkGateWay
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@SpringBootApplication
public class LivkGateWay {
    public static void main(String[] args) {
        LivkSpring.run(LivkGateWay.class, args);
    }
}
