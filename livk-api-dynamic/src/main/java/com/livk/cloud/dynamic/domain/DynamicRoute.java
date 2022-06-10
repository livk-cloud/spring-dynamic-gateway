package com.livk.cloud.dynamic.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * DynamicRoute
 * </p>
 *
 * @author livk
 * @date 2021/11/10
 */
@Data
@NoArgsConstructor
@TableName(value = "dynamic_route")
public class DynamicRoute implements Serializable {

	public static final String COL_ID = "id";

	public static final String COL_URI = "uri";

	public static final String COL_PREDICATES = "predicates";

	public static final String COL_FILTERS = "filters";

	public static final String COL_METADATA = "metadata";

	public static final String COL_ORDER = "order";

	public static final String COL_DESCRIPTION = "description";

	public static final String COL_STATUS = "status";

	public static final String COL_INSERT_TIME = "insert_time";

	public static final String COL_UPDATE_TIME = "update_time";

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 路由id
	 */
	@TableId(value = "id", type = IdType.INPUT)
	private String id;

	/**
	 * url地址
	 */
	@TableField(value = "uri")
	private String uri;

	/**
	 * 断言
	 */
	@TableField(value = "predicates")
	private String predicates;

	/**
	 * 过滤器
	 */
	@TableField(value = "filters")
	private String filters;

	/**
	 * 元数据
	 */
	@TableField(value = "metadata")
	private String metadata;

	/**
	 * 排序
	 */
	@TableField(value = "`order`")
	private Integer order;

	/**
	 * 描述
	 */
	@TableField(value = "description")
	private String description;

	/**
	 * 状态值:0-禁用 1-启用
	 */
	@TableField(value = "`status`")
	private Integer status;

	/**
	 * 插入时间
	 */
	@TableField(value = "insert_time", fill = FieldFill.INSERT)
	private Date insertTime;

	/**
	 * 更新时间
	 */
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

}
