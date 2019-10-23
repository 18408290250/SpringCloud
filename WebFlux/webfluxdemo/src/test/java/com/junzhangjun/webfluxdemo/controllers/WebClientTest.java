package com.junzhangjun.webfluxdemo.controllers;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;


public class WebClientTest {

    @Test
    public void test1(){
        Mono<String> bodyMono = WebClient.create()
                .get()
                .uri("http://localhost:8080/user/find?id=1")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                // The retrieve() method is the easiest way to get a response body and decode it
                .retrieve()
                //ServerResponse 的 bodyToMono 方法把响应内容转换成类 String 的对象，最终得到的结果是 Mono<String>对象
                .bodyToMono(String.class);

        //等待请求完成并得到所产生的类 String 的对象
        System.out.println(bodyMono.block());
    }


    @Test
    public void test2(){
        Mono<String> bodyMono = WebClient.create()
                .get()
                .uri("http://localhost:8080/user/list")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToMono(String.class);
        System.out.println(bodyMono.block());
    }

    //使用 WebClient 访问 SSE 服务
    //由于 SSE 服务的响应是一个消息流，
    //需要使用 flatMapMany 把 Mono<ServerResponse>转换成一个 Flux<ServerSentEvent>对象，
    // 通过方法 BodyExtractors.toFlux 来完成的，其中的参数 new ParameterizedTypeReference<ServerSentEvent<String>>() {}表明了响应消息流中的内容是 ServerSentEvent 对象。
    // 由于 SSE 服务器会不断地发送消息，这里我们只是通过 buffer 方法来获取前 10 条消息并输出
    @Test
    public void test3(){
        final WebClient client = WebClient.create();
        client.get()
                .uri("http://localhost:8080/sse/randomNumbers")
                .accept(MediaType.TEXT_EVENT_STREAM)
                //  exchange 的作用是发送请求并得到以 Mono<ServerResponse>表示的 HTTP 响应
                .exchange()
                .flatMapMany(response -> response.body(BodyExtractors.toFlux(new ParameterizedTypeReference<ServerSentEvent<String>>() {
                })))
                .filter(sse -> Objects.nonNull(sse.data()))
                .map(ServerSentEvent::data)
                .buffer(10)
                .doOnNext(System.out::println)
                .blockFirst();
    }

}