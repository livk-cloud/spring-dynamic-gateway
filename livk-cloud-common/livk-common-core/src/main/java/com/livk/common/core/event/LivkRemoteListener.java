package com.livk.common.core.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.lang.Nullable;

/**
 * <p>
 * LivkRemoteListener
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Slf4j
public class LivkRemoteListener implements ApplicationListener<LivkRemoteApplicationEvent>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(@Nullable LivkRemoteApplicationEvent event) {
        log.info("event:{} Listener", event);
        var remoteHandlerMap = applicationContext.getBeansOfType(LivkRemoteHandler.class);
        remoteHandlerMap.values().stream().sorted((r1, r2) -> {
                    var order1 = AnnotationUtils.findAnnotation(r1.getClass(), Order.class);
                    var order2 = AnnotationUtils.findAnnotation(r2.getClass(), Order.class);
                    if (order2 == null) {
                        return 1;
                    }
                    if (order1 == null) {
                        return -1;
                    }
                    return -(order1.value() - order2.value());
                }).peek(livkRemoteHandler -> log.info("handler:{}", livkRemoteHandler))
                .forEach(livkRemoteHandler -> livkRemoteHandler.remoteHandler(event));
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
