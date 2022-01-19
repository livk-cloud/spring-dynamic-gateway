package com.livk.common.core.result;

import com.livk.common.core.constant.LivkResultEnum;
import com.livk.common.core.exception.LivkException;

/**
 * <p>
 * R
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
public record R<T>(int code, String msg, T data) {

    public static <T> R<T> result(LivkResultEnum livkResultEnum, T data) {
        return new R<>(livkResultEnum.getCode(), livkResultEnum.getMsg(), data);
    }

    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(T data) {
        return new R<>(2000, Constant.SUCCESS, data);
    }

    public static <T> R<T> error(String msg, T data) {
        return new R<>(5001, msg, data);
    }

    public static <T> R<T> error(String msg) {
        return error(msg, null);
    }

    public static <T> R<T> error(LivkException e) {
        return new R<>(e.getCode(), e.getMessage(), null);
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
