package com.junzhangjun.webfluxdemo.filter;

import io.netty.buffer.ByteBufAllocator;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;


@Component
public class BaseFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        return webFilterChain.filter(serverWebExchange);
    }

//    protected  String bodyData="";
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        ServerHttpRequest serverHttpRequest = exchange.getRequest();
//        ServerHttpResponse response = exchange.getResponse();
//        String method = serverHttpRequest.getMethodValue();
//        if ("POST".equals(method)) {
//            //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
//            URI uri = serverHttpRequest.getURI();
//            ServerHttpRequest request = serverHttpRequest.mutate().uri(uri).build();
//            DataBuffer bodyDataBuffer = stringBuffer(bodyData);
//            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
//            request = new ServerHttpRequestDecorator(request) {
//                @Override
//                public Flux<DataBuffer> getBody() {
//                    return bodyFlux;
//                }
//            };
//            //封装request，传给下一级
//            return chain.filter(exchange.mutate().request(request).build());
//        } else if ("GET".equals(method)) {
//            return chain.filter(exchange);
//        }
//        return chain.filter(exchange);
//    }
//
//    private DataBuffer stringBuffer(String value) {
//        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
//
//        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
//        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
//        buffer.write(bytes);
//        return buffer;
//    }

}
