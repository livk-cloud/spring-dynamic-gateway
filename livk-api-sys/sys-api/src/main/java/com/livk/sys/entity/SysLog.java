package com.livk.sys.entity;

import lombok.Builder;
import lombok.Data;

import java.net.InetAddress;
import java.util.Map;

/**
 * <p>
 * SysLog
 * </p>
 *
 * @author livk
 * @date 2022/3/24
 */
@Data
@Builder
public class SysLog {
    private String methodName;

    private Map<String, Object> params;

    private Object result;

    private InetAddress ip;

    private Long runtime;
}
