package com.junzhangjun.webfluxdemo.controllers;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.Arrays;


public class ReactiveStreamTest {
    public static void main(String[] args) {

        String[] str = {"1","2","3"};

        // 订阅者
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {

            private Subscription subscription;

            // 发布者调用订阅者的这个方法来异步传递订阅
            // 建立订阅关系的时候调用
            @Override
            public void onSubscribe(Subscription subscription) {
                // 保存订阅关系，需要同它来给发布者响应
                this.subscription = subscription;

                // 请求一个数据
                this.subscription.request(1);
            }

            // 发布者调用这个方法传递数据给订阅者
            @Override
            public void onNext(Integer item) {
                // 接收一个数据，处理
                System.out.println("接收到数据："+item);

                // 处理完再调用request再请求一个数据
                this.subscription.request(1);

                // 或者已经达到了目标，调用cancel告诉发布者不再接收数据了
                //  this.subscription.cancel();
            }

            @Override
            public void onError(Throwable throwable) {
                // 出现异常(列如处理数据的时候产生了异常)
                throwable.printStackTrace();

                //告诉发布者，后面不再接收数据
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                // 发布者关闭的时候触发
                System.out.println("数据传输完了");
            }
        };



        // jdk8的stream
        Flux.fromArray(str).map(s -> Integer.parseInt(s))
                // 终止操作
                // jdk9的reactive stream
                .subscribe(subscriber);


        //  对比 reactor里面Flux和Mono就是stream，他的最终操作就是 subscribe
//        Flux.fromArray(str)
//                .map(s -> Integer.parseInt(s))
//                .filter(x ->  x>1 )
//                .subscribe(subscriber);
//
//        Arrays.stream(str)
//                .map(s -> Integer.parseInt(s))
//                .filter( x -> x>1)
//                .forEach(System.out::println);








    }


}
