package com.junzhang.webflux_lambdademo.handler;

import com.junzhang.webflux_lambdademo.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

/**
 * 注册处理handler类
 *
 * 实现 HandlerFunction 中的 Mono<T> handle(ServerRequest var1);唯一抽象方法
 */
@Component
public class HelloHandler {

    //ServerResponse 是对响应的封装，可以设置响应状态，响应头，响应正文。
    //比如 ok 代表的是 200 响应码、MediaType 枚举是代表这文本内容类型、返回的是 String 的对象。
    //这里用 Mono 作为返回对象，是因为返回包含了一个 ServerResponse 对象，而不是多个元素

    public Mono<ServerResponse> hello(ServerRequest request) {
        Optional<String> name = request.queryParam("name");
        MultiValueMap<String, String> stringStringMultiValueMap = request.queryParams();
        //  toSingleValueMap
        Map<String, String> stringStringMap = stringStringMultiValueMap.toSingleValueMap();
        //  getFirst
        stringStringMultiValueMap.getFirst("name");

        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello:"+ name.orElseGet(() -> "无")));
    }

    public Mono<ServerResponse> helloCity(ServerRequest request) {
        return ServerResponse.ok().body(sayHelloCity(request), String.class);
    }

    private Mono<String> sayHelloCity(ServerRequest request) {
        Optional<String> cityParamOptional = request.queryParam("city");
        if (!cityParamOptional.isPresent()) {
            throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "request param city is ERROR");
        }
        return Mono.just("Hello," + cityParamOptional.get());
    }


}
