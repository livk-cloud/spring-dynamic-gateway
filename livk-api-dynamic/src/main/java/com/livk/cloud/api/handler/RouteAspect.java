package com.livk.cloud.api.handler;

import com.livk.common.core.event.LivkRemoteApplicationEvent;
import com.livk.common.core.support.SpringContextHolder;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
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
public class RouteAspect {

    private final BusProperties busProperties;

    /**
     * 需添加配置文件，设置通知那个serviceId
     * 例如"api-gateway:9852:**"
     */
    @After(value = "execution(void com.livk.cloud.api.handler.RedisRouteHandler.*(..))")
    public void refresh() {
        SpringContextHolder.publishEvent(new LivkRemoteApplicationEvent(busProperties.getId(), RouteEventHolder::get));
        RouteEventHolder.clear();
    }
}
