package com.kris.common.log.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/18 9:35
 * @since JDK 11
 */
@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.log.datasource")
public class LogDataSourceProperties {
    private Class<? extends DataSource> type;

    private String driverClassName;

    private String url;

    private String username;

    private String password;
}
