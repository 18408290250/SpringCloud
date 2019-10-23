package com.junzhangjun.webfluxdemo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
public class FluxController {

    // 日志记录器
    Logger logger = LoggerFactory.getLogger(getClass());


    @GetMapping("/get1")
    private String get1(){
        logger.info("get1 start");
        String result = createStr();
        logger.info("get1 end");
        return result;
    }


    /**
     * 返回的mono实际上是流，但是controller没有调用流的终止操作，
     * 不会阻塞controller线程,所以get2 start 与 get2 end 之间的时间非常的短
     * （流的惰性求值）
     * @return
     */
    @GetMapping("/get2")
    private Mono<String> get2(){
        logger.info("get2 start");
        Mono.fromSupplier(() -> createStr());
        Mono<String> result = Mono.fromSupplier(() -> createStr());
        logger.info("get2 end");
        return result;
    }

    private String createStr() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "some string";
    }


}
