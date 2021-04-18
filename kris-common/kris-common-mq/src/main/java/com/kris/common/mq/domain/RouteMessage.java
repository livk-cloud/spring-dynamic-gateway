package com.kris.common.mq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/18 9:33
 * @since JDK 11
 */
@Data
@AllArgsConstructor
public class RouteMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * routeId
     */
    private String routingKey;
    /**
     * 操作类型、insert、update、delete
     */
    private String type;
    /**
     * 删除routeId
     */
    private String routingKeyDel;
    /**
     * 是否刷新
     */
    private boolean routeIsRefresh;
}
