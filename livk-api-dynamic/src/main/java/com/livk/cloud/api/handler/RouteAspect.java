package com.livk.cloud.api.handler;

import com.livk.common.core.event.LivkRemoteApplicationEvent;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * <p>
 * RouteAspect
 * </p>
 *
 * @author livk
 * @date 2021/11/9
 */
@Aspect
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RouteAspect  {

    private final BusProperties busProperties;

    private final ApplicationContext applicationContext;

    /**
     * 需添加配置文件，设置通知那个serviceId
     * 例如"api-gateway:9852:**"
     */
    @After(value = "execution(void com.livk.cloud.api.handler.RedisRouteHandler.*(..))")
    public void refresh() {
        applicationContext.publishEvent(new LivkRemoteApplicationEvent(busProperties.getId(), () -> RedisRouteHandler.STREAM_BUS_EVENT));
    }
}
