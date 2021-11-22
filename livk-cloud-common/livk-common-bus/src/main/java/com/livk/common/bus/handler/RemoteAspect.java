package com.livk.common.bus.handler;

import com.livk.common.bus.annotation.LivkEventPublish;
import com.livk.common.bus.event.LivkRemoteEvent;
import com.livk.common.core.support.SpringContextHolder;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.springframework.cloud.bus.BusProperties;

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
    public void publishEvent(LivkEventPublish livkEventPublish) {
        SpringContextHolder.publishEvent(new LivkRemoteEvent(busProperties.getId(), livkEventPublish::value));
    }
}
