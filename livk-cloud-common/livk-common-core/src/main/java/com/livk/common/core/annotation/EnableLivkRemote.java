package com.livk.common.core.annotation;

import com.livk.common.core.event.LivkRemoteListener;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * LivkRemote
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(LivkRemoteListener.class)
public @interface EnableLivkRemote {
}
