package com.livk.common.core.support;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * HttpConfiguration
 * </p>
 *
 * @author livk
 * @date 2022/3/31
 */
@Configuration(proxyBeanMethods = false)
public class HttpConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return getRestTemplate(new OkHttpClient().newBuilder()
                .connectionPool(new ConnectionPool(200, 300, TimeUnit.SECONDS))
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .hostnameVerifier((s, sslSession) -> true)
                .build());
    }

    @Bean
    @ConditionalOnBean(OkHttpClient.class)
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(OkHttpClient okHttpClient) {
        return getRestTemplate(okHttpClient);
    }

    private RestTemplate getRestTemplate(OkHttpClient okHttpClient) {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory(okHttpClient));
    }
}

