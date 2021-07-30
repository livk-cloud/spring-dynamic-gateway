package com.kris.admin.route.controller;

import com.kris.admin.route.model.DynamicRoute;
import com.kris.admin.route.model.KrisRouteVo;
import com.kris.admin.route.service.DynamicRouteService;
import com.kris.common.core.result.R;
import com.kris.common.core.result.R.Constant;
import com.kris.common.core.util.BeanUtil;
import com.kris.common.log.annotation.KrisLog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: kris
 * @Date: 2021/7/9
 * @Description:
 * @Since: JDK11
 */
@RestController
@RequestMapping("/dynamic/route")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DynamicRouteController {

  private final DynamicRouteService dynamicRouteService;

  @KrisLog(description = "查询所有路由信息", isSaveParamAndReturn = true)
  @GetMapping()
  public R<?> listAll() {
    var dynamicRoutes = dynamicRouteService.list();
    return R.ok(Constant.SUCCESS, dynamicRoutes);
  }

  @GetMapping("/{id}")
  public R<?> getById(@PathVariable String id) {
    var dynamicRoute = dynamicRouteService.getById(id);
    return dynamicRoute != null ? R.ok(Constant.SUCCESS, dynamicRoute) : R.error(Constant.ERROR);
  }

  @PostMapping
  public R<?> save(@RequestBody KrisRouteVo vo) {
    var dynamicRoute = BeanUtil.copy(vo, DynamicRoute.class);
    var save = dynamicRouteService.saveAndSend(dynamicRoute);
    return save ? R.ok(Constant.SUCCESS) : R.error(Constant.ERROR);
  }

  @PutMapping()
  public R<?> update(@RequestBody KrisRouteVo vo) {
    var dynamicRoute = BeanUtil.copy(vo, DynamicRoute.class);
    var update = dynamicRouteService.updateByIdAndSend(dynamicRoute);
    return update ? R.ok(Constant.SUCCESS) : R.error(Constant.ERROR);
  }

  @DeleteMapping("/{id}")
  public R<?> delete(@PathVariable String id) {
    var delete = dynamicRouteService.removeByIdAndSend(id);
    return delete ? R.ok(Constant.SUCCESS) : R.error(Constant.ERROR);
  }

  @PostMapping("/refresh")
  public R<?> refresh() {
    var refresh = dynamicRouteService.refresh();
    return refresh ? R.ok(Constant.SUCCESS) : R.error(Constant.ERROR);
  }
}
