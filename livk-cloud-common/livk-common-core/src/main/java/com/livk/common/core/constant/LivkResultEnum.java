package com.livk.common.core.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * LivkResultEnum
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Getter
@RequiredArgsConstructor
public enum LivkResultEnum {

    /**
     *
     */
    ERROR(5001, "失败"),
    /**
     *
     */
    SUCCESS(2000, "成功"),
    /**
     *
     */
    ROUTE_ID_IS_EXIST(4001, "路由id已经存在！"),
    /**
     *
     */
    ROUTE_DOT_EXIST(4002, "路由信息不存在！");

    private final int code;

    private final String msg;
}
