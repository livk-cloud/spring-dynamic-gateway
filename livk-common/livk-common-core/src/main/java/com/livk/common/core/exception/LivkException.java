package com.livk.common.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * LivkException
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@Getter
@RequiredArgsConstructor
public class LivkException extends RuntimeException {

	private final int code;

	private final String message;

}
