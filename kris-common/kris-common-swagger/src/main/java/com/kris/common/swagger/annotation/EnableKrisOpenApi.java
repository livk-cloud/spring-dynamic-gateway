package com.kris.common.swagger.annotation;

import com.kris.common.swagger.GateWaySwaggerAutoConfiguration;
import com.kris.common.swagger.SwaggerAutoConfigurer;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/** @Author: kris @Date: 2021/7/7 @Description: @Since: JDK11 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfigurer.class, GateWaySwaggerAutoConfiguration.class})
public @interface EnableKrisOpenApi {}
