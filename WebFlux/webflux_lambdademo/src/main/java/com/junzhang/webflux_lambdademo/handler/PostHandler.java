package com.junzhang.webflux_lambdademo.handler;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PostHandler {
    /**
     * 获取提交的application/json数据
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> find(ServerRequest serverRequest){
        Mono<Map> body = serverRequest.bodyToMono(Map.class);
        return body.flatMap(map -> {
            String name = (String) map.get("name");
            System.out.println(name);
            return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON)
                    .body(BodyInserters.fromObject(name));
        });
    }

    public Mono<ServerResponse> findform(ServerRequest request){

//        Mono<MultiValueMap<String, String>> formData = request.body(BodyExtractors.toFormData());
        // 获取form data,在方法中添加ServerWebExchange对象，使用方法getFormData
        ServerWebExchange exchange = request.exchange();
        Mono<MultiValueMap<String, String>> formData =exchange.getFormData();

        return formData.flatMap(map -> {
            System.out.println(map.get("name").get(0));
            System.out.println(map.get("sex").get(0));
            return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON)
                    .body(BodyInserters.fromObject(map));
        });
    }
}
