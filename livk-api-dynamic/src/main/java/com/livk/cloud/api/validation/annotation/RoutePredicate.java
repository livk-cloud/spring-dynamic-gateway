package com.livk.cloud.api.validation.annotation;

import com.livk.cloud.api.validation.config.RoutePredicateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>
 * RoutePredicate
 * </p>
 *
 * @author livk
 * @date 2021/11/12
 */
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = RoutePredicateValidator.class)
public @interface RoutePredicate {

	String message() default "{com.livk.cloud.api.validation.annotation.RoutePredicate.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
