package com.livk.cloud.api.domain;

import com.livk.cloud.api.domain.route.Filter;
import com.livk.cloud.api.domain.route.Predicate;
import lombok.Data;

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

    private String description;

    private Integer status;

    private String id;

    private List<Predicate> predicates;

    private List<Filter> filters;

    private String uri;

    private Map<String, Object> metadata;

    private Integer order;
}
