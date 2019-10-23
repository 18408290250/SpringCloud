package com.junzhangjun.webfluxdemo.completeFuture;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureTest {
    public static void main(String[] args) {

        CompletableFuture.supplyAsync(() ->{
            System.out.println("①异步任务开始："+Thread.currentThread().getName());
            return "①return";
        }).thenApplyAsync(r -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("①异步任务Apply_1开始"+Thread.currentThread().getName());
            return r+222;
        }).whenComplete((r,e) -> System.out.println("异步任务Apply_1结束"))
                .thenApplyAsync(r -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("①异步任务Apply_2开始"+Thread.currentThread().getName());
            return r+333;
        })
                .whenComplete((r,e) -> System.out.println(r)).join();
    }

    /**
     * 结果分析：
     * ①：在我们没有指定线程池的情况下CompletableFuture默认使用的是ForkjoinPool,而ForkjoinPoll的线程则是守护线程,
     *     主线程运行完了jvm就直接退出而不会管守护线程是否还在执行,所以需要join()
     *
     * ②：虽然在链式的每一个调用里都指定了异步,整个方法链依然使用的是同一个线程,
     *     在链式调用里方法的运行是串行的,但对于外面调用它的线程来说,它依然是异步的
     */

}
