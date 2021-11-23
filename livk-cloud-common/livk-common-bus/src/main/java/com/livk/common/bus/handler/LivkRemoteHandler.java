package com.livk.common.bus.handler;

import com.livk.common.bus.event.LivkRemoteEvent;

/**
 * <p>
 * LivkRemoteHandler
 * </p>
 *
 * @author livk
 * @date 2021/11/22
 */
public interface LivkRemoteHandler {

	void remoteHandler(LivkRemoteEvent livkRemoteEvent);

}
