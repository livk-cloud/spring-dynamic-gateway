package com.livk.cloud.dynamic.support;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 * DateMetaObjectHandler
 * </p>
 *
 * @author livk
 * @date 2021/11/10
 */
@Component
public class DateMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		this.strictInsertFill(metaObject, "insertTime", Date::new, Date.class);
		this.strictInsertFill(metaObject, "updateTime", Date::new, Date.class);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.strictUpdateFill(metaObject, "updateTime", Date::new, Date.class);
	}

}
