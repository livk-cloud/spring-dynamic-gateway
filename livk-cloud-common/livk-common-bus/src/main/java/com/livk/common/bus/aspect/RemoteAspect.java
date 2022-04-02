package com.livk.common.bus.aspect;

import com.livk.common.bus.annotation.LivkEventPublish;
import com.livk.common.bus.event.LivkRemoteEvent;
import com.livk.common.core.support.SpringContextHolder;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

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
        if (livkEventPublish == null) {
            var signature = (MethodSignature) joinPoint.getSignature();
            livkEventPublish = AnnotationUtils.findAnnotation(signature.getMethod(), LivkEventPublish.class);
            Assert.notNull(livkEventPublish, "LivkEventPublish is null");
        }
        String value = SpringContextHolder.resolvePlaceholders(livkEventPublish.value());
        SpringContextHolder.publishEvent(new LivkRemoteEvent(busProperties.getId(), () -> value));
    }

}
