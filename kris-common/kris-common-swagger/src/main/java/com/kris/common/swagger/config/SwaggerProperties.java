package com.kris.common.swagger.config;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
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

  /** swagger是否启用默认false */
  private Boolean enable = Boolean.FALSE;

  /** swagger的扫描路径 */
  private String basePackage = "";

  /** 是否排除error路径 */
  private Boolean enableExcludeErrorPath = true;

  /** 是否排除Actuator路径 */
  private Boolean enableExcludeActuatorPath = true;
  /** swagger排除路径 */
  private List<String> excludePath;
  /** title 如: 系统接口详情 */
  private String title = "";
  /** 服务文件介绍 */
  private String description = "";
  /** 许可证 */
  private String license = "";
  /** 许可证URL */
  private String licenseUrl = "";
  /** 服务条款网址 */
  private String termsOfServiceUrl = "";
  /** 版本 */
  private String version = "V1.0";
  /** 作者 */
  private String author = "";
  /** url */
  private String url = "";
  /** email */
  private String email = "";

  private Contact contact = new Contact();

  @Data
  @NoArgsConstructor
  public static class Contact {

    /** 联系人 */
    private String name = "";

    /** 联系人url */
    private String url = "";

    /** 联系人email */
    private String email = "";
  }
}
