package com.livk.cloud.api.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * LivkEventPublish
 * </p>
 *
 * @author livk
 * @date 2021/11/15
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LivkEventPublish {

	String value();

}
