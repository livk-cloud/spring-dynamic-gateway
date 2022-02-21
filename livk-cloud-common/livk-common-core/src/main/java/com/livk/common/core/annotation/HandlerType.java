package com.livk.common.core.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * HandlerType
 * </p>
 *
 * @author livk
 * @date 2022/2/19
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HandlerType {

	String value();

}
