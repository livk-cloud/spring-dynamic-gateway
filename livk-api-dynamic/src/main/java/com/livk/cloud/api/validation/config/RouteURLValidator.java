package com.livk.cloud.api.validation.config;

import com.livk.cloud.api.validation.annotation.RouteURL;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.UrlValidator;

/**
 * <p>
 * RouteURLValidator
 * </p>
 *
 * @author livk
 * @date 2021/11/12
 */
public class RouteURLValidator implements ConstraintValidator<RouteURL, String> {

    /**
     * http lb转发
     */
    private static final String HTTP_LOAD_BALANCER_PREFIX = "lb://";
    /**
     * websocket lb转发
     */
    private static final String WEBSOCKET_LOAD_BALANCER_PREFIX = "lb:ws://";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.startsWith(HTTP_LOAD_BALANCER_PREFIX) || new UrlValidator().isValid(s)
               || s.startsWith(WEBSOCKET_LOAD_BALANCER_PREFIX);
    }
}
