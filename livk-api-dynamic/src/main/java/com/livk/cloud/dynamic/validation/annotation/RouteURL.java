package com.livk.cloud.dynamic.validation.annotation;

import com.livk.cloud.dynamic.validation.config.RouteURLValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>
 * RouteURL
 * </p>
 *
 * @author livk
 * @date 2021/11/12
 */
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = RouteURLValidator.class)
public @interface RouteURL {

	String message() default "{com.livk.cloud.api.validation.annotation.RouteURL.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
