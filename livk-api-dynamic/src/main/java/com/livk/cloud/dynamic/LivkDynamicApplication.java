package com.livk.cloud.dynamic;

import com.livk.common.core.spring.LivkSpring;
import com.livk.common.springdoc.annotation.EnableLivkOpenApi;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>
 * LivkDynamic
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@EnableLivkOpenApi
@EnableFeignClients("com.livk.sys.feign")
@SpringBootApplication
public class LivkDynamicApplication {

	public static void main(String[] args) {
		LivkSpring.run(LivkDynamicApplication.class, args);
	}

}
