package com.livk.sys.feign.factory;

import com.livk.common.core.result.R;
import com.livk.sys.feign.RemoteSysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * <p>
 * RemoteSysLogFallback
 * </p>
 *
 * @author livk
 * @date 2022/3/24
 */
@Slf4j
public class RemoteSysLogFallback implements FallbackFactory<RemoteSysLogService> {

	@Override
	public RemoteSysLogService create(Throwable cause) {
		log.error("系统服务调用失败:{}", cause.getMessage());
		return sysLog -> R.error("日志保存失败:" + cause.getMessage(), false);
	}

}
