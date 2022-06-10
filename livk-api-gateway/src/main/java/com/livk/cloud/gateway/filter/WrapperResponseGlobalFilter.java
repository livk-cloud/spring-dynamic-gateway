package com.livk.cloud.gateway.filter;

import com.livk.common.core.util.SysUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;
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
		var uri = exchange.getRequest().getURI();
		if (uri.getPath().endsWith("/v3/api-docs")) {
			return chain.filter(exchange);
		}
		String contentType = originalResponse.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
		if (contentType == null || StringUtils.countOccurrencesOf(contentType, "application/json") == 0) {
			return chain.filter(exchange);
		}
		var decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
			@Nonnull
			@Override
			public Mono<Void> writeWith(@Nonnull Publisher<? extends DataBuffer> body) {
				if (body instanceof Flux) {
					var fluxBody = (Flux<? extends DataBuffer>) body;
					return super.writeWith(fluxBody.map(dataBuffer -> {
						var content = new byte[dataBuffer.readableByteCount()];
						dataBuffer.read(content);
						DataBufferUtils.release(dataBuffer);
						var result = new String(content, StandardCharsets.UTF_8);
						return bufferFactory.wrap(SysUtils.packageResult(result).getBytes(StandardCharsets.UTF_8));
					}));
				}
				return super.writeWith(body);
			}
		};
		return chain.filter(exchange.mutate().response(decoratedResponse).build());
	}

}
