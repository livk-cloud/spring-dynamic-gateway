package com.livk.cloud.api.controller;

import com.livk.cloud.api.domain.RedisRoute;
import com.livk.cloud.api.service.DynamicRouteService;
import com.livk.common.log.annotation.LivkLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
@RequestMapping("route")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RouteController {

    private final DynamicRouteService dynamicRouteService;

    @GetMapping
    public List<RedisRoute> list() {
        return dynamicRouteService.selectList();
    }

    @GetMapping("/{routeId}")
    public RedisRoute getByRouteId(@PathVariable("routeId") String routeId) {
        return dynamicRouteService.getById(routeId);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public Boolean addNewRoute(@RequestBody @Valid RedisRoute redisRoute) {
        return dynamicRouteService.saveOrUpdate(redisRoute);
    }

    @DeleteMapping("/{routeId}")
    public Boolean deleteRoute(@PathVariable("routeId") String routeId) {
        return dynamicRouteService.delete(routeId);
    }

    @PostMapping("reload")
    public Boolean reload() {
        return dynamicRouteService.reload();
    }

}
