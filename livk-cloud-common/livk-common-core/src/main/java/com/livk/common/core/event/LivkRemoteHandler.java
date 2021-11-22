package com.livk.common.core.event;

/**
 * <p>
 * 实现此类即可对 {@link com.livk.common.core.event.LivkRemoteApplicationEvent} 进行处理
 * 链式处理模式通过注解{@link org.springframework.core.annotation.Order} 进行排序 需将实现类注入IOC
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
public interface LivkRemoteHandler {

	void remoteHandler(LivkRemoteApplicationEvent livkRemoteApplicationEvent);

}
