package com.kris.api.gateway.filter;

import com.kris.common.core.util.SysUtil;
import java.nio.charset.StandardCharsets;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** @Author: kris @Date: 2021/7/12 @Description: @Since: JDK11 */
@Component
public class WrapperResponseGlobalFilter implements GlobalFilter, Ordered {

  @Override
  public int getOrder() {
    // -1 is response write filter, must be called before that
    return -2;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    var originalResponse = exchange.getResponse();
    var bufferFactory = originalResponse.bufferFactory();
    var decoratedResponse =
        new ServerHttpResponseDecorator(originalResponse) {
          @Override
          public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            if (body instanceof Flux) {
              var fluxBody = (Flux<? extends DataBuffer>) body;
              return super.writeWith(
                  fluxBody.map(
                      dataBuffer -> {
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
            // if body is not a flux. never got there.
            return super.writeWith(body);
          }
        };
    // replace response with decorator
    return chain.filter(exchange.mutate().response(decoratedResponse).build());
  }
}
