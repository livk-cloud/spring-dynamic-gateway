package com.livk.common.mapstruct;

import com.livk.common.mapstruct.factory.MapstructFactoryRegister;
import com.livk.common.mapstruct.support.ConverterRepository;
import com.livk.common.mapstruct.support.InmemoryConverterRepository;
import com.livk.common.mapstruct.support.MapstructService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * MapstructAutoConfiguration
 * </p>
 *
 * @author livk
 * @date 2022/5/11
 */
@AutoConfiguration
public class MapstructAutoConfiguration {

	@Bean
	public MapstructService mapstructService(ConverterRepository repository) {
		return new MapstructService(repository);
	}

	@Bean
	public ConverterRepository converterRepository() {
		return new InmemoryConverterRepository();
	}

}
