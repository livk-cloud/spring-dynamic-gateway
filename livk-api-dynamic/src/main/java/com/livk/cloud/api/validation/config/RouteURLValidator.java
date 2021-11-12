package com.livk.cloud.api.validation.config;

import com.livk.cloud.api.validation.annotation.RouteURL;

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

    private static final String URL_REGEX = "^(?=^.{3,255}$)(http(s)?://)?(www\\.)?[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:\\d+)*(/\\w+\\.\\w+)*$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.startsWith(LOAD_BALANCER_PREFIX) || s.matches(URL_REGEX);
    }
}
