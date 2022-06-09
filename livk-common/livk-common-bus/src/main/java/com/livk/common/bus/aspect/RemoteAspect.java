package com.livk.common.bus.aspect;

import com.livk.common.bus.annotation.LivkEventPublish;
import com.livk.common.bus.event.LivkRemoteEvent;
import com.livk.common.core.support.SpringContextHolder;
import com.livk.common.core.util.SpringUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * <p>
 * RemoteAspect
 * </p>
 *
 * @author livk
 * @date 2021/11/22
 */
@RequiredArgsConstructor
public class RemoteAspect {

	private final BusProperties busProperties;

	/**
	 * 需添加配置文件，设置通知那个serviceId 例如"api-gateway:9852:**"
	 */
	@After("@annotation(livkEventPublish)")
	public void publishEvent(JoinPoint joinPoint, LivkEventPublish livkEventPublish) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		if (livkEventPublish == null) {
			livkEventPublish = AnnotationUtils.findAnnotation(method, LivkEventPublish.class);
			Assert.notNull(livkEventPublish, "LivkEventPublish is null");
		}
		String value = SpringUtils.parseSpEL(method, joinPoint.getArgs(), livkEventPublish.value());
		SpringContextHolder.publishEvent(new LivkRemoteEvent(busProperties.getId(), () -> value));
	}

}
