package com.livk.cloud.dynamic.support;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.livk.cloud.dynamic.converter.DynamicRouteConverter;
import com.livk.cloud.dynamic.domain.DynamicRoute;
import com.livk.cloud.dynamic.domain.RedisRoute;
import com.livk.cloud.dynamic.service.DynamicRouteService;
import com.livk.common.core.util.JacksonUtils;
import com.livk.common.mapstruct.utils.MapstructUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * <p>
 * </p>
 *
 * @author livk
 * @date 2021/11/10
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InitializationRoute implements ApplicationRunner {

	private final DynamicRouteService dynamicRouteService;

	private final DynamicRouteConverter converter;

	private final DataSource dataSource;

	private final Environment env;

	@Value("classpath:sql/initData.json")
	private Resource initData;

	@Value("classpath:sql/table.sql")
	private Resource initSql;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		ScriptUtils.executeSqlScript(dataSource.getConnection(), initSql);
		var serviceName = env.getProperty("spring.application.name");
		var dynamicRoute = dynamicRouteService
				.getOne(Wrappers.lambdaQuery(DynamicRoute.class).eq(DynamicRoute::getUri, "lb://" + serviceName));
		if (dynamicRoute == null) {
			var redisRoute = JacksonUtils.toBean(initData.getInputStream(), RedisRoute.class);
			redisRoute.setId(serviceName);
			redisRoute.setUri("lb://" + serviceName);
			redisRoute.setDescription("this is " + serviceName + " route!");
			dynamicRoute = MapstructUtils.converter(redisRoute, DynamicRoute.class);
			dynamicRouteService.saveOrUpdate(dynamicRoute);
		}
		log.info("Route Info init is :{}", dynamicRouteService.reload());
	}

}
