package com.livk.cloud.api.config;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

/**
 * <p>
 * CustomNotifier
 * </p>
 *
 * @author livk
 * @date 2022/2/22
 */
@Slf4j
@Component
public class CustomNotifier extends AbstractEventNotifier {

	protected CustomNotifier(InstanceRepository repository) {
		super(repository);
	}

	@NonNull
	@Override
	protected Mono<Void> doNotify(@Nullable InstanceEvent event, @Nullable Instance instance) {
		Assert.notNull(event, "event not bu null");
		Assert.notNull(instance, "instance not bu null");
		return Mono.fromRunnable(() -> {
			if (event instanceof InstanceStatusChangedEvent statusChangedEvent) {
				log.info("Instance {} ({}) is {}", instance.getRegistration().getName(), event.getInstance(),
						statusChangedEvent.getStatusInfo().getStatus());
			}
			else {
				log.info("Instance {} ({}) {}", instance.getRegistration().getName(), event.getInstance(),
						event.getType());
			}
		});
	}

}
