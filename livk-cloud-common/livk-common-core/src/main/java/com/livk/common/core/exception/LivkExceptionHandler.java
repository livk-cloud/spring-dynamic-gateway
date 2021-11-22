package com.livk.common.core.exception;

import com.livk.common.core.result.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 * LivkExceptionHandler
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@RestControllerAdvice
public class LivkExceptionHandler {

	@ExceptionHandler(LivkException.class)
	public R<String> krisExceptionHandler(LivkException e) {
		e.printStackTrace();
		return R.error(e.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public R<String> exceptionHandler(Exception e) {
		e.printStackTrace();
		return R.error("Server error");
	}

}
