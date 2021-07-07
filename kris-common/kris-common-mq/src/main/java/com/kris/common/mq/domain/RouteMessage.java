package com.kris.common.mq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/18 9:33
 * @since JDK 11
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class RouteMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * routeId
     */
    private String routingKey;
    /**
     * 操作类型、insert、update、delete
     */
    private Integer type;
    /**
     * 是否刷新
     */
    private Boolean routeIsRefresh;
}
