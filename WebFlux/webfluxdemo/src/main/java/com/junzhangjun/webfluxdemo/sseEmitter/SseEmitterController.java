package com.junzhangjun.webfluxdemo.sseEmitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * localhost:8080/sseemitter/
 *
 * 发现 spring的默认容器Netty对sseemitter好像不支持，tomcat可以
 *
 *
 *  HTTP流用于在Http相应中同时推送多个事件
 *  该对象可以被用于发送多个对象，
 *   通常我们使用到的@ResponseBody只能返回一个对象
 *   执行emitter.complete()才一次性返回
 */
@RestController
public class SseEmitterController {
//    @RequestMapping(value = "/sseemitter")
//    public SseEmitter handleSSE() {
//        SseEmitter emitter = new SseEmitter();
//
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//
//        executor.execute(() ->
//        {
//            try {
//                for (int i = 0; i < 10; i++) {
//                    randomDelay();
//                    emitter.send(new Date());
//                }
//                emitter.complete();
//
//            } catch (IOException e) {
//                emitter.completeWithError(e);
//            }
//        });
//        executor.shutdown();
//        return emitter;
//    }
//
//    private void randomDelay() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
}
