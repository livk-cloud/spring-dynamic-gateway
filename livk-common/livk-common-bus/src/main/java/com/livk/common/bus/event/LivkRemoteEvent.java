package com.livk.common.bus.event;

import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * <p>
 * LivkRemoteEvent
 * </p>
 *
 * @author livk
 * @date 2021/11/22
 */
public class LivkRemoteEvent extends RemoteApplicationEvent {

    public LivkRemoteEvent() {
        super();
    }

    public LivkRemoteEvent(String originService, Destination destination) {
        this("livk", originService, destination);
    }

    /**
     * {@see org.springframework.cloud.bus.event.RemoteApplicationEvent#RemoteApplicationEvent(Object,
     *String, String)}
     */
    public LivkRemoteEvent(Object source, String originService, Destination destination) {
        super(source, originService, destination);
    }

}
