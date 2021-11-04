package com.livk.cloud.api.controller;

import com.livk.cloud.api.converter.DynamicRouteConverter;
import com.livk.cloud.api.domain.RedisRoute;
import com.livk.common.log.annotation.LivkLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * RouteController
 * </p>
 *
 * @author livk
 * @date 2021/11/3
 */
@Slf4j
@LivkLog
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RouteController {

    @PostMapping("route")
    public Boolean addNewRoute(@RequestBody RedisRoute redisRoute) {
        log.info("{}", DynamicRouteConverter.INSTANCE.getTarget(redisRoute));
        return true;
    }
}
