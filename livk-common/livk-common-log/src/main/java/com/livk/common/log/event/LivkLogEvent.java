package com.livk.common.log.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * <p>
 * LivkLogEvent
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Getter
public class LivkLogEvent extends ApplicationEvent {

    private final String serviceName;

    public LivkLogEvent(Object source, String serviceName) {
        super(source);
        this.serviceName = serviceName;
    }

}
