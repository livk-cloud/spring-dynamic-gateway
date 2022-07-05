package com.livk.common.mapstruct.factory;

import com.livk.common.mapstruct.converter.MapstructRegistry;
import com.livk.common.mapstruct.support.GenericMapstructService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;

/**
 * <p>
 * MapstructFactory
 * </p>
 *
 * @author livk
 * @date 2022/5/11
 */
@RequiredArgsConstructor
public class MapstructFactory implements InitializingBean {

    private final MapstructRegistry registry;

    private final ListableBeanFactory beanFactory;

    @Override
    public void afterPropertiesSet() {
        GenericMapstructService.addBeans(registry, beanFactory);
    }

}
