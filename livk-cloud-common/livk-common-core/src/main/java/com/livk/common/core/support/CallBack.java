package com.livk.common.core.support;

/**
 * <p>
 * CallBack
 * </p>
 *
 * @author livk
 * @date 2022/2/8
 */
public interface CallBack {

	/**
	 * 回调执行方法
	 */
	void executor();

	/**
	 * 本回调任务名称
	 */
	default String getCallBackName() {
		return Thread.currentThread().getId() + ":" + this.getClass().getName();
	}

}
