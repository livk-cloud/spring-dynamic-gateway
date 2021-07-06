package com.kris.common.swagger.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 12:07
 * @since JDK 11
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    /**
     * swagger是否启用默认false
     */
    private Boolean enable = Boolean.FALSE;

    /**
     * swagger的扫描路径
     */
    private String basePackage = "";

    /**
     * 是否排除error路径
     */
    private Boolean enableExcludeErrorPath = Boolean.TRUE;

    /**
     * 是否排除Actuator路径
     */
    private Boolean enableExcludeActuatorPath = Boolean.FALSE;
    /**
     * swagger排除路径
     */
    private String[] excludePath = {};
    /**
     * title 如: 系统接口详情
     */
    private String title = "";
    /**
     * 服务文件介绍
     */
    private String description = "";
    /**
     * 服务条款网址
     */
    private String termsOfServiceUrl = "";

    /**
     * 版本
     */
    private String version = "V1.0";
    /**
     * 作者
     */
    private String author = "";

    /**
     * url
     */
    private String url = "";

    /**
     * email
     */
    private String email = "";
}
