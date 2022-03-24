package com.livk.common.log.event;

import com.livk.common.core.util.JacksonUtils;
import com.livk.sys.entity.SysLog;
import com.livk.sys.feign.RemoteSysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

import javax.annotation.Nonnull;

/**
 * <p>
 * LivkLogEventListener
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Slf4j
@RequiredArgsConstructor
public class LivkLogEventListener implements ApplicationListener<LivkLogEvent> {

    private final RemoteSysLogService remoteSysLogService;

    @Override
    public void onApplicationEvent(@Nonnull LivkLogEvent event) {
        log.info("serviceName:{}-->log:{}", event.getServiceName(), JacksonUtils.toJson(event.getSource()));
        remoteSysLogService.save((SysLog) event.getSource());
    }
}
