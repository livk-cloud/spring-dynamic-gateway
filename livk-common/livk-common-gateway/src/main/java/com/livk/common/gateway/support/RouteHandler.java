package com.livk.common.gateway.support;

import com.livk.common.bus.event.LivkRemoteEvent;
import com.livk.common.bus.handler.LivkRemoteHandler;
import com.livk.common.core.support.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;

/**
 * <p>
 * RouteHandler
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@Slf4j
public class RouteHandler implements LivkRemoteHandler {

    @Override
    public void remoteHandler(LivkRemoteEvent livkRemoteEvent) {
        SpringContextHolder.publishEvent(new RefreshRoutesEvent(this));
        log.info("Spring Gateway RefreshRoutesEvent publish!");
    }

}
