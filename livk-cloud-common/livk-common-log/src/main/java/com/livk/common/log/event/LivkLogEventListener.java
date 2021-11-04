package com.livk.common.log.event;

import com.livk.common.core.util.JacksonUtil;
import lombok.RequiredArgsConstructor;
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

    private static final ExecutorService executor = new ThreadPoolExecutor(20, 100,
            60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10));

    @Override
    public void onApplicationEvent(@Nullable LivkLogEvent event) {
        executor.execute(new LogThead(event, log));
    }
}

@RequiredArgsConstructor
class LogThead implements Runnable {

    private final LivkLogEvent event;
    private final Logger log;

    @Override
    public void run() {
        log.info("serviceName:{}-->log:{}", event.getServiceName(), JacksonUtil.objToStr(event.getSource()));
    }
}
