package com.livk.common.bus.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * LivkEventPublish
 * </p>
 *
 * @author livk
 * @date 2021/11/22
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LivkEventPublish {

    String value();
}
