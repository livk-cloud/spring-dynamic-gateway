package com.livk.common.bus.listener;

import com.livk.common.bus.event.LivkRemoteEvent;
import com.livk.common.bus.handler.LivkRemoteHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * <p>
 * LivkRemoteListener
 * </p>
 *
 * @author livk
 * @date 2021/11/22
 */
@Slf4j
@RequiredArgsConstructor
public class LivkRemoteListener implements ApplicationListener<LivkRemoteEvent> {

    private final List<LivkRemoteHandler> livkRemoteHandlerList;

    @Override
    public void onApplicationEvent(@Nonnull LivkRemoteEvent event) {
        log.info("event:{} Listener", event);
        livkRemoteHandlerList.forEach(livkRemoteHandler -> livkRemoteHandler.remoteHandler(event));
    }
}
