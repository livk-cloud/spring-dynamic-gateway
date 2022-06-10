package com.livk.common.core.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

	private final int code;

	private final String msg;

	private final T data;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public R(@JsonProperty("code") int code, @JsonProperty("msg") String msg, @JsonProperty("data") T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	// @JsonCreator
	// public R(@JsonProperty int code, @JsonProperty String msg) {
	// this(code, msg, null);
	// }

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
