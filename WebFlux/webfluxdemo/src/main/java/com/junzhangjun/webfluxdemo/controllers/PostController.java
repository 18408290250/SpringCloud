package com.junzhangjun.webfluxdemo.controllers;

import com.junzhangjun.webfluxdemo.domain.User;
import com.junzhangjun.webfluxdemo.service.UserService;
import io.netty.buffer.ByteBufAllocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;

import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RestController
@RequestMapping("/postController")
public class PostController {

    /**
     * 通过@RequestBody获取提交的application/json数据
     * @param
     * @return
     */
    @PostMapping(value = "/json",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<String> json(@RequestBody String user) {
        System.out.println("json:"+user);
        return Mono.just(user);
    }

    /**
     * 通过实体映射获取post的请求体 application/json数据
     * @param user
     * @return
     */
    @PostMapping(value = "/jsonS",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<String> jsonS(@RequestBody User user) {
        System.out.println("jsonS:"+ user);
        return Mono.just(user.toString());

    }

    /**
     * 通过实体映射获取表单数据
     * request：Content-Type: application/x-www-form-urlencoded
     * @param user
     * @return
     */
    @PostMapping(value = "/form", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<User> save(User user) {
        System.out.println("form:"+user);
        return Mono.justOrEmpty(user);
    }

    /**
     * 通过ServerWebExchange获取表单数据
     * request：Content-Type: application/x-www-form-urlencoded
     * @param exchange
     * @return
     */
    @PostMapping(value = "/formS", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<String> save(ServerWebExchange exchange) {
        Mono<MultiValueMap<String, String>> formData =exchange.getFormData();
        return formData.flatMap(map -> {
            System.out.println("formS："+map);
            return Mono.justOrEmpty(map.toString());
        });
    }
}
