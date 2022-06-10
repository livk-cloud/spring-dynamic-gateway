package com.livk.common.springdoc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * SwaggerProperties
 * </p>
 *
 * @author livk
 * @date 2021/11/9
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "springdoc")
public class SpringdocProperties {

	/**
	 * swagger是否启用默认false
	 */
	private Boolean enable = Boolean.FALSE;

	/**
	 * swagger的扫描路径
	 */
	private String basePackage = "";

	/**
	 * swagger排除路径
	 */
	private List<String> excludePath = new ArrayList<>();

	/**
	 * 需要排除的服务
	 */
	private List<String> ignoreProviders = new ArrayList<>();

	/**
	 * title 如: 系统接口详情
	 */
	private String title = "";

	/**
	 * 服务文件介绍
	 */
	private String description = "";

	/**
	 * 许可证
	 */
	private String license = "";

	/**
	 * 许可证URL
	 */
	private String licenseUrl = "";

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

	private Contact contact = new Contact();

	@Data
	public static class Contact {

		/**
		 * 联系人
		 */
		private String name = "";

		/**
		 * 联系人url
		 */
		private String url = "";

		/**
		 * 联系人email
		 */
		private String email = "";

	}

}
