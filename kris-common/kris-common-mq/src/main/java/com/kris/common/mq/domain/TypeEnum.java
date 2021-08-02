package com.kris.common.mq.domain;

import lombok.Getter;

/** @Author: kris @Date: 2021/7/9 @Description: @Since: JDK11 */
@Getter
public enum TypeEnum {
  /** 插入 */
  INSERT,
  /** 修改 */
  UPDATE,
  /** 删除 */
  DELETE,
  /** 无 */
  NONE;
}
