package com.kris.common.mq.events;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 19:34
 * @since JDK 11
 */
@FunctionalInterface
public interface BusReceiver {

    /**
     * rabbitmq消息接收，实现此接口
     *
     * @param obj
     */
    void handleMessage(Object obj);
}