package com.livk.common.gateway.config;

import com.livk.common.gateway.constant.RouteType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>
 * LivkRouteProperties
 * </p>
 *
 * @author livk
 * @date 2021/12/6
 */
@Data
@ConfigurationProperties(prefix = "livk.gateway.route")
public class LivkRouteProperties {

	private RouteType type = RouteType.IN_MEMORY;

}
