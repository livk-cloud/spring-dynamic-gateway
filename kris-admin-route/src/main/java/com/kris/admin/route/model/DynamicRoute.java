package com.kris.admin.route.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kris.common.core.jackson.Json;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 网关路由表 @Author: kris @Date: 2021/7/6 @Description: ${Description} @Since: JDK11 */
@ApiModel
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "dynamic_route")
public class DynamicRoute implements Serializable, Json {

  /** 路由id */
  @TableId(value = "route_id", type = IdType.INPUT)
  @ApiModelProperty(value = "路由id")
  private String routeId;

  /** uri路径 */
  @TableField(value = "uri")
  @ApiModelProperty(value = "uri路径")
  private String uri;

  /** #跳转类型，0时从注册中心获取地址，1直接跳转网络地址 */
  @TableField(value = "route_type")
  @ApiModelProperty(value = "#跳转类型，0时从注册中心获取地址，1直接跳转网络地址")
  private Integer routeType;

  /** 判定器 */
  @TableField(value = "predicates")
  @ApiModelProperty(value = "判定器")
  private String predicates;

  /** 过滤器 */
  @TableField(value = "filters")
  @ApiModelProperty(value = "过滤器")
  private String filters;

  /** 加载顺序 */
  @TableField(value = "`order`")
  @ApiModelProperty(value = "加载顺序")
  private Integer order;

  /** 描述 */
  @TableField(value = "description")
  @ApiModelProperty(value = "描述")
  private String description;

  /** 状态：1-有效，0-无效 */
  @TableField(value = "`status`")
  @ApiModelProperty(value = "状态：1-有效，0-无效")
  private Integer status;

  /** 创建时间 */
  @TableField(value = "create_time", fill = FieldFill.INSERT)
  @ApiModelProperty(value = "创建时间")
  private Date createTime;

  /** 更新时间 */
  @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
  @ApiModelProperty(value = "更新时间")
  private Date updateTime;

  @Serial private static final long serialVersionUID = 1L;

  public static final String COL_ROUTE_ID = "route_id";

  public static final String COL_URI = "uri";

  public static final String COL_ROUTE_TYPE = "route_type";

  public static final String COL_PREDICATES = "predicates";

  public static final String COL_FILTERS = "filters";

  public static final String COL_ORDER = "order";

  public static final String COL_DESCRIPTION = "description";

  public static final String COL_STATUS = "status";

  public static final String COL_CREATE_TIME = "create_time";

  public static final String COL_UPDATE_TIME = "update_time";
}
