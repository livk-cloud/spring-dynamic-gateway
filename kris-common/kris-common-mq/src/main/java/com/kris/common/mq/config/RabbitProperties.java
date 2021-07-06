package com.kris.common.mq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @Author: kris
 * @Date: 2021/7/6
 * @Description:
 * @Since: JDK11
 */
@Data
@RefreshScope
@ConfigurationProperties("kris.rabbit")
public class RabbitProperties {

    private String exchange;

    private String queue;

    private String binding;
}
