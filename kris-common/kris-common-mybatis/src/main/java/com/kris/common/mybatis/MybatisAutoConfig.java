package com.kris.common.mybatis;

import com.kris.common.mybatis.config.MybatisPlusMetaObjectHandler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author: kris
 * @Date: 2021/7/6
 * @Description:
 * @Since: JDK11
 */
@Configuration(
    proxyBeanMethods = false
)
@Import(MybatisPlusMetaObjectHandler.class)
@EnableAutoConfiguration
public class MybatisAutoConfig {

}
