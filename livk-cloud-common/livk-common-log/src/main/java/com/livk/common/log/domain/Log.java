package com.livk.common.log.domain;

import lombok.Data;

import java.net.InetAddress;
import java.util.Map;

/**
 * <p>
 * Log
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Data
public class Log {

	private String method;

	private Map<String, Object> params;

	private Object result;

	private InetAddress ip;

	private Long runtime;

}
