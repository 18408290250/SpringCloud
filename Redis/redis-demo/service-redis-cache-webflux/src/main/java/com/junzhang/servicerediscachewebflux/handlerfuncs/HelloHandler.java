package com.junzhang.servicerediscachewebflux.handlerfuncs;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.redis.core.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;



@Component
public class HelloHandler {

    private static final Logger logger = LoggerFactory.getLogger(HelloHandler.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    @Autowired
    ReactiveRedisTemplate reactiveRedisTemplate;

    private String key = "tiketsInfo";

    // http://localhost:8000/service5/hello
    // http://localhost:8010/hello
    public Mono<ServerResponse> hello(ServerRequest request) {

        // 1.从缓存中获取信息
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        boolean hasKey = stringRedisTemplate.hasKey(key);
        if (hasKey) {
            String message = operations.get(key);
            logger.info("cache >> " + message);
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(message));
        }

        // 2.从 数据库 中获取信息
        try {
            TimeUnit.SECONDS.sleep(4);
            logger.info("db查询end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        jsonObject.put("info","今天是林俊杰的成都演唱会哟");
        jsonObject.put("price","1000");
        jsonObject.put("songs","好听的歌");
        String db_message = jsonObject.toString();

        // 3.插入缓存
        operations.set(key,db_message,60 * 10, TimeUnit.SECONDS);  // 同时设置过期时间 10分钟

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(db_message));

    }


    // http://localhost:8000/service5/hi
    // http://localhost:8010/hi
    public Mono<ServerResponse> hi(ServerRequest request) {

        Mono mono = reactiveRedisTemplate.opsForValue().get(key);
        //  问题1： toProcessor().peek() 不可用
        try {
            logger.info(mono.toProcessor().peek().toString());
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        // not cache ,from db
        Mono<ServerResponse> db = ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.fromSupplier(() -> {

                    logger.info("is null is null is null");
                    //  1.当redis里不存在缓存时，到数据库查询
                    try {
                        TimeUnit.SECONDS.sleep(4);
                        logger.info("db查询end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    jsonObject.put("info", "今天是岳云鹏的成都演唱会哟");
                    jsonObject.put("price", "1000");
                    jsonObject.put("songs", "好听的歌");
                    String db_message = jsonObject.toString();

                    //  2.写入缓存 10秒失效
                    Instant inst1 = Instant.now();  //当前的时间
                    Instant inst2 = inst1.plus(Duration.ofSeconds(10));     //当前时间+10秒后的时间
                    reactiveRedisTemplate.opsForValue().set(key, db_message,Duration.between(inst1, inst2)).subscribe();

                    return db_message;
                }),String.class);


        // 问题2： 流式写法 若有缓存取缓存，否则从数据库中获取并set cache
        // 若使用map  => 报错 java.lang.ClassCastException: reactor.core.publisher.MonoMapFuseable cannot be cast to org.springframework.web.reactive.function.server.ServerResponse
        // 使用flaMap =>  需要注意：若get(key) 得到的Mono为空,则不会执行flateMap里面的操作，需要另使用switchIfEmpty判断等
        return reactiveRedisTemplate.opsForValue().get(key).flatMap(str -> {
            logger.info("redis get cache：" + str);
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(str));
        }).switchIfEmpty(db);
    }

}
