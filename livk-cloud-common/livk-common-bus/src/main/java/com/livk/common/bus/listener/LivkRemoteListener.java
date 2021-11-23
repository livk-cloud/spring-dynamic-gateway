package com.livk.common.bus.listener;

import com.livk.common.bus.event.LivkRemoteEvent;
import com.livk.common.bus.handler.LivkRemoteHandler;
import com.livk.common.core.support.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.core.OrderComparator;
import org.springframework.lang.Nullable;

/**
 * <p>
 * LivkRemoteListener
 * </p>
 *
 * @author livk
 * @date 2021/11/22
 */
@Slf4j
public class LivkRemoteListener implements ApplicationListener<LivkRemoteEvent> {

	@Override
	public void onApplicationEvent(@Nullable LivkRemoteEvent event) {
		log.info("event:{} Listener", event);
		var remoteHandlerMap = SpringContextHolder.getApplicationContext().getBeansOfType(LivkRemoteHandler.class);
		remoteHandlerMap.values().stream().sorted(OrderComparator.INSTANCE)
				.peek(livkRemoteHandler -> log.info("handler:{}", livkRemoteHandler))
				.forEach(livkRemoteHandler -> livkRemoteHandler.remoteHandler(event));
	}

}
