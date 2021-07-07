package com.kris.common.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 11:35
 * @since JDK 11
 */
@Getter
@AllArgsConstructor
public class KrisException extends RuntimeException {

  private final int code;

  private final String message;
}
