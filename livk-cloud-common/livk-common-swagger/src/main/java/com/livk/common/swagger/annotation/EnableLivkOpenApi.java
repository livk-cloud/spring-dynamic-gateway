package com.livk.common.swagger.annotation;

import com.livk.common.swagger.LivkGateWaySwaggerAutoConfiguration;
import com.livk.common.swagger.LivkSwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.lang.annotation.*;

/**
 * <p>
 * EnableLivkOpenApi
 * </p>
 *
 * @author livk
 * @date 2021/11/9
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableOpenApi
@Import({LivkGateWaySwaggerAutoConfiguration.class, LivkSwaggerAutoConfiguration.class})
public @interface EnableLivkOpenApi {
}
