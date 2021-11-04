package com.livk.cloud.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
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
 * @date 2021/11/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dynamic_route")
public class DynamicRoute implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @TableField(value = "uri")
    private String uri;

    @TableField(value = "predicates")
    private String predicates;

    @TableField(value = "filters")
    private String filters;

    @TableField(value = "metadata")
    private String metadata;

    @TableField(value = "\"order\"")
    private Integer order;

    @TableField(value = "description")
    private String description;

    @TableField(value = "\"status\"")
    private Integer status;

    @TableField(value = "insert_time")
    private Date insertTime;

    @TableField(value = "update_time")
    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;

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
}
