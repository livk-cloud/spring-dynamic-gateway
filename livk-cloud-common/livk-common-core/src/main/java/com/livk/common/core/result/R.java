package com.livk.common.core.result;

import com.livk.common.core.constant.LivkResultEnum;
import com.livk.common.core.exception.LivkException;
import lombok.Getter;

/**
 * <p>
 * R
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Getter
public class R<T> {

    /**
     * 返回状态码
     */
    private final int code;
    /**
     * 返回消息
     */
    private final String msg;
    /**
     * 返回数据
     */
    private final T data;

    private R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> R<T> result(int code, String msg, T data) {
        return new R<>(code, msg, data);
    }

    public static <T> R<T> result(LivkResultEnum livkResultEnum, T data) {
        return result(livkResultEnum.getCode(), livkResultEnum.getMsg(), data);
    }

    public static <T> R<T> ok(T data) {
        return result(2000, "success", data);
    }

    public static <T> R<T> error(String msg, T data) {
        return result(5001, msg, data);
    }

    public static <T> R<T> error(String msg) {
        return error(msg, null);
    }

    public static <T> R<T> error(LivkException e) {
        return result(e.getCode(), e.getMessage(), null);
    }

    public static class Constant {

        private Constant() {
        }

        public static final String SUCCESS = "success";

        public static final String ERROR = "error";

        public static final String CODE = "code";
        public static final String MSG = "msg";
        public static final String DATA = "data";
    }
}
