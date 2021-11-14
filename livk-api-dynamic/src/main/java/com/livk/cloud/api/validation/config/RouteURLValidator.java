package com.livk.cloud.api.validation.config;

import com.livk.cloud.api.validation.annotation.RouteURL;
import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p>
 * RouteURLValidator
 * </p>
 *
 * @author livk
 * @date 2021/11/12
 */
public class RouteURLValidator implements ConstraintValidator<RouteURL, String> {

    private static final String LOAD_BALANCER_PREFIX = "lb://";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.startsWith(LOAD_BALANCER_PREFIX) || new UrlValidator().isValid(s);
    }
}
