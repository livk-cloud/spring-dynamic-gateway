package com.livk.cloud.api.support;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.livk.cloud.api.converter.DynamicRouteConverter;
import com.livk.cloud.api.domain.DynamicRoute;
import com.livk.cloud.api.domain.RedisRoute;
import com.livk.cloud.api.service.DynamicRouteService;
import com.livk.common.bus.annotation.LivkEventPublish;
import com.livk.common.core.util.JacksonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * <p>此处选{@link org.springframework.boot.ApplicationRunner}</p>
 * <p>考虑到{@link com.livk.common.bus.handler.RemoteAspect#publishEvent(LivkEventPublish)}</p>
 * <p>方法需要引用{@link org.springframework.context.ApplicationContext}</p>
 * <p>暂定排除掉{@link org.springframework.beans.factory.InitializingBean}</p>
 *
 * @author livk
 * @date 2021/11/10
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InitializationRoute implements ApplicationRunner {

	private final DynamicRouteService dynamicRouteService;

	@Value("classpath:sql/initData.json")
	private Resource initData;

	private final Environment env;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		var serviceName = env.getProperty("spring.application.name");
		var dynamicRoute = dynamicRouteService
				.getOne(Wrappers.lambdaQuery(DynamicRoute.class).eq(DynamicRoute::getUri, "lb://" + serviceName));
		if (dynamicRoute == null) {
			var redisRoute = JacksonUtils.toBean(initData.getInputStream(), RedisRoute.class);
			redisRoute.setId(serviceName);
			redisRoute.setUri("lb://" + serviceName);
			redisRoute.setDescription("this is " + serviceName + " route!");
			dynamicRoute = DynamicRouteConverter.INSTANCE.getTarget(redisRoute);
			dynamicRouteService.saveOrUpdate(dynamicRoute);
			log.info("Route Info init is :{}", dynamicRouteService.reload());
		}
	}

}
