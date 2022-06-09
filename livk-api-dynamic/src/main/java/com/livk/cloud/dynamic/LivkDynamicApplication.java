package com.livk.cloud.dynamic;

import com.livk.common.core.spring.LivkSpring;
import com.livk.common.mapstruct.annotation.EnableConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * LivkDynamic
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@EnableConverter
@SpringBootApplication
public class LivkDynamicApplication {

	public static void main(String[] args) {
		LivkSpring.run(LivkDynamicApplication.class, args);
	}

}
