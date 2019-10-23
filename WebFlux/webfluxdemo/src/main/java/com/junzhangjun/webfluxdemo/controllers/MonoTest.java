package com.junzhangjun.webfluxdemo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.Console;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * 更详细的文档：Mono与Flux
 * projectreactor.io/docs/core/release/reference/#flux
 *
 *
 */
public class MonoTest {
    public static void main(String[] args) {

        //======================================================== ①：创建 =====================================================

        // empty()：创建一个不包含任何元素，只发布结束消息的序列
//        Mono.empty().subscribe(System.out::println);


        // just()：可以指定序列中包含的全部元素。创建出来的 Mono序列在发布这些元素之后会自动结束。
//        Mono.just("mono")
//                .log()
//                .subscribe(System.out::println);


        // justOrEmpty()：从一个 Optional 对象或可能为 null 的对象中创建 Mono。
        //只有 Optional 对象中包含值或对象不为 null 时，Mono 序列才产生对应的元素。
//        Mono.justOrEmpty(null).subscribe(System.out::println);
//        Mono.justOrEmpty("mono").subscribe(System.out::println);
//        Mono.justOrEmpty(Optional.of("mono")).subscribe(System.out::println);



        // from
//        Mono.from(Mono.just(2)).subscribe(System.out::println);
//        Mono.fromCallable(() -> "callable run ").subscribe(System.out::println);
//        Mono.fromSupplier(() -> "create from supplier").subscribe(System.out::println);
//        Mono.fromRunnable(() -> IntStream.range(0,2)).subscribe(System.out::println);
        CompletableFuture<String> retrun = CompletableFuture.completedFuture("retrun");

//        CompletableFuture completableFuture = new CompletableFuture();
//        completableFuture.completeAsync(() -> {
//            System.out.println("执行");
//           return IntStream.range(1, 100).reduce(0, Integer::sum);
//        });

        Mono.fromFuture(retrun);
//        Mono.fromFuture(() -> completableFuture);
//
//        Mono.from(Mono.just("hello"))
//                // 消费第一个
//                .doOnNext(x -> System.out.println(x+"addddddd"))
//                // 消费第二个
//                .doOnNext(System.out::println)
//                .block(); //

        //  在subscribe中处理异常
//        Mono.fromRunnable(() -> {
//            System.out.println("thread run");
//            throw new RuntimeException("thread run error");
//        }).subscribe(System.out::println,System.err::println);
        // thread run
        // java.lang.RuntimeException: thread run error

        //  onErrorReturn 处理异常
//        Mono.fromRunnable(() -> {
//            System.out.println("thread run");
//            throw new RuntimeException("thread run error");
//        }).onErrorReturn("exception")
//        .subscribe(System.out::println);
        // thread run
        // exception


        // never()：创建一个不包含任何消息通知的序列。
//        Mono.never().subscribe(System.out::println);

        // 通过 create()方法来使用 MonoSink（MonoSink 是对 Subscriber 的封装，
//        Mono.create(sink -> sink.success("sink")).subscribe(System.out::println);


        // delay(Duration duration)和 delayMillis(long duration)：
        // 创建一个 Mono 序列，在指定的延迟时间之后，产生数字 0 作为唯一值。
//        long start = System.currentTimeMillis();
//        Disposable disposable = Mono.delay(Duration.ofSeconds(4)).subscribe(n -> {
//            System.out.println("生产数据源："+ n);
//            System.out.println("当前线程ID："+ Thread.currentThread().getId() + ",生产到消费耗时："+ (System.currentTimeMillis() - start));
//        });
//        System.out.println("主线程"+ Thread.currentThread().getId() + "耗时："+ (System.currentTimeMillis() - start));
//        try {
//            Thread.currentThread().join(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        // error(Throwable error)：创建一个只包含错误消息的序列。
        Mono.error(new RuntimeException("error")).subscribe(System.out::println, System.err::println);


        //  onErrorResume 处理异常
//        Mono.fromRunnable(() -> {
//            System.out.println("thread run");
//            throw new IllegalArgumentException("thread run error");
//        }) .onErrorResume(e -> {
//            if (e instanceof IllegalStateException) {
//                return Mono.just("RuntimeException");
//            } else if (e instanceof IllegalArgumentException) {
//                return Mono.just(-1);
//            }
//            return Mono.empty();
//        })
//        .subscribe(System.out::println); // thread run  -1

        // 异常后可重试
//        Mono.fromRunnable(() -> {
//            System.out.println("thread run");
//            throw new RuntimeException("thread run error");
//        }).retry(2)
//            .subscribe(System.out::println);



//        Mono.from(Mono.just("hello"))
//                .doOnNext(msg-> System.out.println("正常①："+msg))
//                .doOnNext(msg->{
////                    try{
////                        int a =  1/0;
////                    }catch (Exception e){
////                        throw  new RuntimeException("模拟一个异常");
////                    }
//                    System.out.println("正常②："+msg);
//                })
//                .doOnNext(msg-> System.out.println("正常③："+msg))
//                .doOnError(msg->System.out.println("进入消费异常逻辑："+msg.getMessage()))
//                .block(Duration.ofSeconds(10));





    }
}
