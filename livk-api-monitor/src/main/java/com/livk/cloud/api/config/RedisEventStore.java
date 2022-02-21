package com.livk.cloud.api.config;

import com.livk.common.core.util.ObjectUtils;
import com.livk.common.redis.support.LivkReactiveRedisTemplate;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.values.InstanceId;
import de.codecentric.boot.admin.server.eventstore.InstanceEventPublisher;
import de.codecentric.boot.admin.server.eventstore.InstanceEventStore;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * <p>
 * RedisEventStore
 * </p>
 *
 * @author livk
 * @date 2022/2/21
 */
@Component
public class RedisEventStore extends InstanceEventPublisher implements InstanceEventStore {

    private static final String INSTANCE_EVENT_KEY = "Event";

    private final ReactiveHashOperations<String, String, List<InstanceEvent>> hashOperations;

    public RedisEventStore(LivkReactiveRedisTemplate reactiveRedisTemplate) {
        hashOperations = reactiveRedisTemplate.opsForHash();
    }

    @Override
    public Flux<InstanceEvent> findAll() {
        return hashOperations.entries(INSTANCE_EVENT_KEY).map(Map.Entry::getValue).flatMapIterable(Function.identity());
    }

    @Override
    public Flux<InstanceEvent> find(InstanceId id) {
        return hashOperations.get(INSTANCE_EVENT_KEY, id.getValue()).flatMapIterable(Function.identity());
    }

    @Override
    public Mono<Void> append(List<InstanceEvent> events) {
        if (events.isEmpty()) {
            return Mono.empty();
        }
        InstanceId id = events.get(0).getInstance();
        if (ObjectUtils.allChecked(o -> !id.equals(o.getInstance()), events)) {
            throw new IllegalArgumentException("'events' must only refer to the same instance.");
        }
        return hashOperations.put(INSTANCE_EVENT_KEY, id.getValue(), events)
                .then();
    }
}
