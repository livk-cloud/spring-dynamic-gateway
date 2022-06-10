package com.livk.common.springdoc.annotation;

import com.livk.common.springdoc.LivkSpringdocAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * EnableLivkOpenApi
 * </p>
 *
 * @author livk
 * @date 2021/11/9
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ LivkSpringdocAutoConfiguration.class })
public @interface EnableLivkOpenApi {

}
