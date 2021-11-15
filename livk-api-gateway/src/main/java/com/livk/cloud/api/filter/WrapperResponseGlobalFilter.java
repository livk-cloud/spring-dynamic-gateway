package com.livk.cloud.api.filter;

import com.livk.common.core.util.SysUtil;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * WrapperResponseGlobalFilter
 * </p>
 *
 * @author livk
 * @date 2021/11/15
 */
@Component
public class WrapperResponseGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return -2;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var originalResponse = exchange.getResponse();
        var bufferFactory = originalResponse.bufferFactory();
        var decoratedResponse =
                new ServerHttpResponseDecorator(originalResponse) {
                    @NonNull
                    @Override
                    public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                        if (body instanceof Flux) {
                            var fluxBody = (Flux<? extends DataBuffer>) body;
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        // probably should reuse buffers
                                        var content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        // 释放掉内存
                                        DataBufferUtils.release(dataBuffer);
                                        var result = new String(content, StandardCharsets.UTF_8);
                                        result = SysUtil.packageResult(result);
                                        var uppedContent = result.getBytes();
                                        return bufferFactory.wrap(uppedContent);
                                    }));
                        }
                        return super.writeWith(body);
                    }
                };
        // replace response with decorator
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }
}
