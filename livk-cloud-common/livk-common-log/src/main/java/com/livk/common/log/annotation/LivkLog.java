package com.livk.common.log.annotation;

import java.lang.annotation.*;

/**
 * <p>
 * LivkLog
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LivkLog {
    String description() default "";

    boolean isSaveParamAndReturn() default false;
}
