package com.livk.cloud.api.domain.route;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * Predicates
 * </p>
 *
 * @author livk
 * @date 2021/11/4
 */
@Data
public class Predicate {
    private String name;

    private Map<String, String> args = new LinkedHashMap<>();
}
