package com.livk.common.log.event;

import com.livk.common.core.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.Nullable;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * LivkLogEventListener
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Slf4j
public class LivkLogEventListener implements ApplicationListener<LivkLogEvent> {

	private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(20, 100, 60L, TimeUnit.SECONDS,
			new ArrayBlockingQueue<>(10), r -> new Thread(null, r, "Livk-log-Thread-", 0, false));

	@Override
	public void onApplicationEvent(@Nullable LivkLogEvent event) {
		EXECUTOR.execute(new LogThread(event, log));
	}

}

record LogThread(LivkLogEvent event, Logger log) implements Runnable {

	@Override
	public void run() {
		log.info("serviceName:{}-->log:{}", event.getServiceName(), JacksonUtils.toJson(event.getSource()));
	}

}
