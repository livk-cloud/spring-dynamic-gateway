package com.livk.common.core.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;

import java.util.Map;

/**
 * <p>
 * LivkRemoteListener
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Configuration
public class LivkRemoteListener implements ApplicationListener<LivkRemoteApplicationEvent>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(LivkRemoteApplicationEvent event) {
        Map<String, LivkRemoteHandler> remoteHandlerMap = applicationContext.getBeansOfType(LivkRemoteHandler.class);
        remoteHandlerMap.values().stream().sorted((r1, r2) -> {
            Order order1 = AnnotationUtils.findAnnotation(r1.getClass(), Order.class);
            Order order2 = AnnotationUtils.findAnnotation(r2.getClass(), Order.class);
            if (order2 == null) {
                return 1;
            }
            if (order1 == null) {
                return -1;
            }
            return -(order1.value() - order2.value());
        }).forEach(livkRemoteHandler -> livkRemoteHandler.remoteHandler(event));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
