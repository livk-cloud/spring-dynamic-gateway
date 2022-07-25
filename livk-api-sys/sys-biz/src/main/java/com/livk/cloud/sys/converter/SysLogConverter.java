package com.livk.cloud.sys.converter;

import com.livk.common.core.util.JacksonUtils;
import com.livk.sys.dto.SysLogDTO;
import com.livk.cloud.sys.entity.SysLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;

/**
 * <p>
 * SysLogConverter
 * </p>
 *
 * @author livk
 * @date 2022/6/9
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {JacksonUtils.class})
public interface SysLogConverter extends Converter<SysLogDTO, SysLog> {

    @Mapping(target = "params", expression = "java(JacksonUtils.toJson(sysLog.getParams()))")
    @Mapping(target = "result", expression = "java(JacksonUtils.toJson(sysLog.getParams()))")
    @Override
    SysLog convert(SysLogDTO source);

}
