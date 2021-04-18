package com.kris.common.mybatis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 11:40
 * @since JDK 11
 */
@Data
@Component
@RefreshScope
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "kris.mybatis")
public class MybatisProperties {

    private String[] insertDate;

    private String[] updateDate;
}
