package com.livk.sys.feign;

import com.livk.common.core.result.R;
import com.livk.sys.dto.SysLogDTO;
import com.livk.sys.feign.factory.RemoteSysLogFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * RemoteSysLogService
 * </p>
 *
 * @author livk
 * @date 2022/3/24
 */
@FeignClient(value = "livk-api-sys", fallbackFactory = RemoteSysLogFallback.class)
public interface RemoteSysLogService {

	@PostMapping("/sysLog")
	R<Boolean> save(@RequestBody SysLogDTO sysLogDTO);

}
