package com.livk.common.log.event;

import com.livk.common.core.support.SpringContextHolder;
import com.livk.sys.dto.SysLogDTO;
import com.livk.sys.feign.RemoteSysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
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

	/**
	 * 监听器单独存储，没有加入到IOC {@link SpringApplication#getListeners()}
	 */
	@Override
	public void onApplicationEvent(@Nonnull LivkLogEvent event) {
		// log.info("serviceName:{}-->log:{}", event.getServiceName(),
		// JacksonUtils.toJson(event.getSource()));
		SpringContextHolder.getBean(RemoteSysLogService.class).save((SysLogDTO) event.getSource());
	}

}
