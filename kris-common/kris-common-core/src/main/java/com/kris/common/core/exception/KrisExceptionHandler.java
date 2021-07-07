package com.kris.common.core.exception;

import com.kris.common.core.result.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: kris
 * @Date: 2021/7/7
 * @Description:
 * @Since: JDK11
 */
@RestControllerAdvice
public class KrisExceptionHandler {

  @ExceptionHandler(KrisException.class)
  public R<?> krisExceptionHandler(KrisException e) {
    return R.error(e);
  }

  @ExceptionHandler(Exception.class)
  public R<?> ExceptionHandler(Exception e) {
    return R.error(e.getMessage());
  }
}
