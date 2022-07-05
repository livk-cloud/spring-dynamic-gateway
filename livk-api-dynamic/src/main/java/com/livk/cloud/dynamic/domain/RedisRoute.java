package com.livk.cloud.dynamic.domain;

import com.livk.cloud.dynamic.domain.route.Filter;
import com.livk.cloud.dynamic.domain.route.Predicate;
import com.livk.cloud.dynamic.validation.annotation.RoutePredicate;
import com.livk.cloud.dynamic.validation.annotation.RouteURL;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * DynamicRoute
 * </p>
 *
 * @author livk
 * @date 2021/11/4
 */
@Data
public class RedisRoute {

    @NotBlank(message = "路由id不能为空")
    private String id;

    @RoutePredicate(message = "断言匹配不能缺少")
    private List<Predicate> predicates;

    private List<Filter> filters;

    @RouteURL(message = "URI表达式不满足")
    private String uri;

    private Map<String, Object> metadata;

    @NotBlank(message = "路由描述不能为空!")
    private String description;

    @Max(value = 1, message = "状态值只能为1或者0")
    @Min(value = 0, message = "状态值只能为1或者0")
    @NotNull(message = "状态值不能为空!")
    private Integer status;

    private Integer order = 0;

}
