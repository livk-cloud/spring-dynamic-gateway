package com.livk.cloud.dynamic.domain.route;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * Filters
 * </p>
 *
 * @author livk
 * @date 2021/11/4
 */
@Data
public class Filter {

	private String name;

	private Map<String, String> args = new LinkedHashMap<>();

}
