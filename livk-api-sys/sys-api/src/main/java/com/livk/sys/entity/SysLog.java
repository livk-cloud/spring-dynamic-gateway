package com.livk.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.net.InetAddress;
import java.util.Date;

/**
 * <p>
 * SysLog
 * </p>
 *
 * @author livk
 * @date 2022/3/24
 */
@Data
@TableName("sys_log")
public class SysLog {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String methodName;

    private String params;

    private String result;

    private InetAddress ip;

    private Long runtime;

    private Date time = new Date();

}
