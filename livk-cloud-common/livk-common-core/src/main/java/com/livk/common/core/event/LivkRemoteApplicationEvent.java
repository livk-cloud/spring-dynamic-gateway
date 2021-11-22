package com.livk.common.core.event;

import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * <p>
 * LivkRemoteApplicationEvent
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
public class LivkRemoteApplicationEvent extends RemoteApplicationEvent {

	public LivkRemoteApplicationEvent() {
		super();
	}

	public LivkRemoteApplicationEvent(String originService, Destination destination) {
		this("livk", originService, destination);
	}

	/**
	 * {@link org.springframework.cloud.bus.event.RemoteApplicationEvent#RemoteApplicationEvent(Object, String, String)}
	 */
	public LivkRemoteApplicationEvent(Object source, String originService, Destination destination) {
		super(source, originService, destination);
	}

}
