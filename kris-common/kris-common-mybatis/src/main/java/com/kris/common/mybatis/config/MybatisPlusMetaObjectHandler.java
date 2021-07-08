package com.kris.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author kris
 * @version 1.0
 * @description: {一句话描述类是干什么的}
 * @date: 2021/4/17 11:38
 * @since JDK 11
 */
@ConditionalOnBean(MybatisProperties.class)
@EnableConfigurationProperties(MybatisProperties.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

  private final MybatisProperties mybatisProperties;

  @Override
  public void insertFill(MetaObject metaObject) {
    for (String insertDate : mybatisProperties.getInsertDate()) {
      this.strictInsertFill(metaObject, insertDate, Date::new, Date.class);
    }
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    for (String updateDate : mybatisProperties.getUpdateDate()) {
      this.strictUpdateFill(metaObject, updateDate, Date::new, Date.class);
    }
  }
}
