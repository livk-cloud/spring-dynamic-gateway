package com.livk.common.core.result;

import com.livk.common.core.constant.LivkResultEnum;
import com.livk.common.core.exception.LivkException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class R<T> {

	private int code;

	private String msg;

	private T data;

	public static <T> R<T> result(LivkResultEnum livkResultEnum, T data) {
		return new R<>(livkResultEnum.getCode(), livkResultEnum.getMsg(), data);
	}

	public static <T> R<T> ok() {
		return ok(null);
	}

	public static <T> R<T> ok(T data) {
		return new R<>(LivkResultEnum.SUCCESS.getCode(), Constant.SUCCESS, data);
	}

	public static <T> R<T> error(String msg, T data) {
		return new R<>(LivkResultEnum.ERROR.getCode(), msg, data);
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
