package com.livk.sys.dto;

import lombok.Data;

import java.net.InetAddress;
import java.util.Map;

/**
 * <p>
 * SysLogDTO
 * </p>
 *
 * @author livk
 * @date 2022/6/9
 */
@Data
public class SysLogDTO {

	private String methodName;

	private Map<String, Object> params;

	private Object result;

	private InetAddress ip;

	private Long runtime;

}
