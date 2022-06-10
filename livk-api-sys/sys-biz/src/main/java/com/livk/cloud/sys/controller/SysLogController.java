package com.livk.cloud.sys.controller;

import com.livk.cloud.sys.service.SysLogService;
import com.livk.common.core.result.R;
import com.livk.common.core.util.SpringUtils;
import com.livk.sys.dto.SysLogDTO;
import com.livk.sys.entity.SysLog;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * SysLogController
 * </p>
 *
 * @author livk
 * @date 2022/6/9
 */
@RequestMapping("/sysLog")
@RestController
@RequiredArgsConstructor
public class SysLogController {

	private final SysLogService sysLogService;

	private final ConversionService conversionService;

	@PostMapping
	public R<Void> save(@RequestBody SysLogDTO sysLogDTO) {
		SysLog sysLog = conversionService.convert(sysLogDTO, SysLog.class);
		return sysLogService.save(sysLog) ? R.ok() : R.error("error");
	}

}
