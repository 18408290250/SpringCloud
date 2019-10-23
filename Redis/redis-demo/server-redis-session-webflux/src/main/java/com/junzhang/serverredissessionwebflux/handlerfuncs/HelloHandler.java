package com.junzhang.serverredissessionwebflux.handlerfuncs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@Component
public class HelloHandler {

    //  request.session().toProcessor().peek()   // 有问题 待解决
    private static final Logger logger = LoggerFactory.getLogger(HelloHandler.class);

    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;

    public Mono<ServerResponse> login(ServerRequest request) {

        request.session()
                .toProcessor()
                .peek().getAttributes()
                .putIfAbsent("user",request.queryParam("userName"));

        request.session()
                .toProcessor()
                .peek().getAttributes()
                .putIfAbsent("sessionId",request.session().toProcessor().peek().getAttribute("X-Auth-Token"));


        Map<String, String> map = new HashMap<>();
        map.put("sessionId", request.session().toProcessor().peek().getAttribute("X-Auth-Token"));
        map.put("user", request.queryParam("userName").get());

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(map));
    }


    public Mono<ServerResponse> index(ServerRequest request) {
        //
      //  logger.info(request.session().toProcessor().peek().getId());
      //  logger.info(request.session().toProcessor().peek().getAttribute("X-Auth-Token"));

        Map<String, String> map = new HashMap<>();
        map.put("sessionId",request.session().toProcessor().peek().getAttribute("X-Auth-Token"));
        map.put("user", request.session().toProcessor().peek().getAttribute("user"));

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(map));
    }








}
