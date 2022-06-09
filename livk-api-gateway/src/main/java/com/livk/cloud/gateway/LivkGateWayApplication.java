package com.livk.cloud.gateway;

import com.livk.common.core.spring.LivkSpring;
import com.livk.common.swagger.annotation.EnableLivkOpenApi;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * LivkGateWay
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@EnableLivkOpenApi
@SpringBootApplication
public class LivkGateWayApplication {

	public static void main(String[] args) {
		LivkSpring.run(LivkGateWayApplication.class, args);
	}

}
