package com.livk.common.gateway.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * <p>
 * LivkRoute
 * </p>
 *
 * @author livk
 * @date 2021/11/2
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LivkRoute extends RouteDefinition {
    /**
     * 描述
     */
    private String description;
    /**
     * 状态：1-有效，0-无效
     */
    private Integer status;
}
