package com.junzhangjun.webfluxdemo.sse;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/sse")
public class SseController {
    @RequestMapping(value = "/data",produces = "text/event-stream;charset=UTF-8")
    public String push(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "data:返回数据"+Math.random()+"\n\n";
    }

    /**
     * 表示这是一个事件流，返回的是Flux类型，推送的间隔为1s，最后take(times)表示推送的次数，没有take表示无限流，times表示推送的次数
     * @return
     */
    @GetMapping(value = "/data1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> sseBook() {
        // take方法，就是获取一定数量的数据，take获取开头几个元素，takeLast 获取最后几个元素，takeWhile 符合条件就获取，takeUntil 直到符合条件的时候才获取
        return Flux.interval(Duration.ofSeconds(1))
                .map(
                        value -> LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"))
                ).take(5);
    }

    /**
     * 每隔一秒产生一个随机数的 SSE 端点。
     * 使用类 ServerSentEvent.Builder 来创建 ServerSentEvent 对象。
     * 指定了事件名称 random，以及每个事件的标识符和数据。
     * 事件的标识符是一个递增的整数，而数据则是产生的随机数。
     * @return
     */
    @GetMapping(value = "/randomNumbers", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Integer>> randomNumbers() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(data -> ServerSentEvent.<Integer>builder()
                        .event("random")
                        .id(Long.toString(data.getT1()))
                        .data(data.getT2())
                        .build());
    }


}
