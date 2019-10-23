package com.junzhangjun.webfluxdemo.controllers;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FluxTest {
    public static void main(String[] args) {

        //======================================================== ①：创建 =====================================================

        // just
//        Flux.just("1").subscribe(System.out::println);
//        Flux.just("1","2","3").subscribe(System.out::println);

        // from
//        Flux.fromArray(new Integer[]{1, 2, 3}).subscribe(System.out::println);
//        Flux.fromStream(Arrays.asList("1","2","3").stream()).subscribe(System.out::println);
//        Flux.fromStream(() -> Arrays.stream(new String[]{"1","2","3"})).subscribe(System.out::println);
//        Flux.fromIterable(Arrays.asList("1","2","3")).subscribe(System.out::println);
//        Flux.from(Mono.just("2")).subscribe(System.out::println);


        //error
//        Flux.error(new RuntimeException()).subscribe(System.out::println);

        //never 创建一个Flux，它永远不会发出任何数据、错误或完成信号。
//        Flux.never().subscribe(System.out::println);

        // empty
//        Flux.empty().subscribe(System.out::println);

        // range
//        Flux.range(1,10).subscribe(System.out::println);


        // create(Consumer<? super FluxSink<T>> emitter)
        //
//        Flux.create((t) -> {
//            t.next("create");
//            t.next("create1");
//            t.complete();
//        }).subscribe(System.out::println);

        // generate(Consumer<SynchronousSink<T>> generator)
        // 同步和逐一的方式来产生 Flux 序列,序列的产生是通过调用所提供的 SynchronousSink 对象的 next()，complete()和 error(Throwable)方法来完成的
        // generate中next只能调1次，否则会报错 reactor.core.Exceptions$ErrorCallbackNotImplemented
//        Flux.generate(t -> {
//            //注意：generate中next只能调用1次
//            t.next("generate");
//            // 注意：通过 next()方法产生一个简单的值，然后通过 complete()方法来结束该序列。如果不调用 complete()方法，所产生的是一个无限序列
////            t.complete();
//        }).subscribe(System.out::println);

        // interval  定时一段时间产生数据，时间参数为 Duration
//        Flux.interval(Duration.of(2, ChronoUnit.SECONDS)).subscribe(System.out::println);
//        //防止程序过早退出，放一个CountDownLatch拦住
//        CountDownLatch latch = new CountDownLatch(1);
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        //======================================================== ②：中间操作 =====================================================

        // buffer，bufferTimeout 根据所包含的元素的最大数量或收集的时间间隔收集到集合
//        Flux<List<Integer>> buffer = Flux.range(1, 100)
//                .buffer(20);
//        buffer.subscribe(x -> System.out.println(x));
//
//         Flux.range(1, 100)
//                .buffer(20).subscribe(System.out::println);

//        Flux.range(1, 100)
//                .bufferTimeout(5,Duration.ofMinutes(1)).subscribe(System.out::println);
//
           // bufferUntil: 收集到直到满足条件(若都不满足条件则都收集了)，使得 Predicate 返回 true 的那个元素可以选择添加到当前集合或下一个集合中
          // bufferWhile: 只有条件满足才收集，一旦值为 false，会立即开始下一次收集
//        Flux.range(1, 10).bufferUntil(i -> i % 5 == 0).subscribe(System.out::println); // [1,2,3,4,5][6.7,8,9,10]
//        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println); // [2][4][6][8][10]


        // windows
       //  window 操作符的作用类似于 buffer，所不同的是 window 操作符是把当前流中的元素收集到另外的 Flux 序列中，因此返回值类型是 Flux<Flux<T>>
//        Flux<Flux<Integer>> window = Flux.range(1, 100)
//                .window(20);
//        Flux<Integer> integerFlux = window.flatMap(x -> x);
//        integerFlux.subscribe(System.out::println);

        // take 提取元素
        // take(long n)，take(Duration timespan)和 takeMillis(long timespan)：按照指定的数量或时间间隔来提取。
        // takeLast(long n)：提取流中的最后 N 个元素。
        // takeUntil(Predicate<? super T> predicate)：提取元素直到 Predicate 返回 true。
        // takeWhile(Predicate<? super T> continuePredicate)： 当 Predicate 返回 true 时才进行提取。
        // takeUntilOther(Publisher<?> other)：提取元素直到另外一个流开始产生元素

//        Flux.range(1, 1000).take(10).subscribe(System.out::println); // 1-10
//        Flux.range(1, 1000).takeLast(10).subscribe(System.out::println); // 991 - 1000
//        Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println); // 1-9
//        Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println); // 1-10

        // reduce 和 reduceWith
//        Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println); // 5050
//        List<Integer> list = Arrays.asList(1,2,3,4,5);
//        Mono<Integer> reduce = Flux.fromIterable(list)
//                .reduce(10, (x,y) -> {
//                    System.out.println("x="+x+",y="+y);
//                    return x+y;
//                });
//        reduce.subscribe(System.out::println); // 25
//        Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println); // 5150




        // merge：把多个Flux"按元素实际产生的顺序"合并
        //mergeSequential 按多个Flux"被订阅的顺序"来合并
//        Flux.merge(
//                Flux.interval(Duration.of(1000, ChronoUnit.MILLIS)).take(5),
//                Flux.interval(Duration.of(1000, ChronoUnit.MILLIS)).take(5))
//                .subscribe(System.out::print);  // 001122334455

        //Sequential 第一个流中的数据先输出，第二个数据流会先放入缓存，等待第一个流。
//        Flux.mergeSequential(
//                Flux.interval(Duration.of(1000, ChronoUnit.MILLIS)).take(5),
//                Flux.interval(Duration.of(1000, ChronoUnit.MILLIS)).take(5)).subscribe(System.out::print);
// 0123401234


        // zipWith 合并元素
//        Flux.just("a", "b")
//                .zipWith(Flux.just("c", "d"))
//                .subscribe(System.out::println); // [a,c][b,d]
//        Flux.just("a", "b")
//                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
//                .subscribe(System.out::println);// [a-c][b-d]

        // zip 合并
//        Mono<Integer> just = Mono.fromSupplier(() -> {
//            System.out.println("生成2"+ Instant.now());
//            return 2;
//        });
//        Mono<Integer> delay = Mono.fromSupplier(() -> {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("生成3"+ Instant.now());
//            return 3;
//        });

        //
//        Mono.zip(items -> {
//            Integer item1 = Integer.class.cast(items[0]);
//            Long item2 = Long.class.cast(items[1]);
//            // Do merge
//            return Stream.of(item1,item2).collect(Collectors.toList());
//        }, just, delay)
//                .subscribe(System.out::println);

//
//        Mono.zip(Mono.fromSupplier(() -> 2), Mono.fromSupplier(() -> 3))
//                .map(tuple -> {
//                    return Stream.of(tuple.getT1(),tuple.getT2()).collect(Collectors.toList());
//                }).doFinally(x -> {
//            System.out.println(x);
//        }).subscribe(System.out::println);


//        Mono.zip(Mono.error(new RuntimeException()), Mono.delay(Duration.ofSeconds(4)))
//                .map(tuple -> {
//                    return Stream.of(tuple.getT1(),tuple.getT2()).collect(Collectors.toList());
//                }).doFinally(x -> {
//                System.out.println(x);
//        }).subscribe(System.out::println);


//        Mono.zip(Mono.fromSupplier(() -> 2), Mono.fromSupplier(() -> 3),(x,y)-> Stream.of(x,y).collect(Collectors.toList()))
//                .subscribe(System.out::println);

        // first
//        Flux.first(Flux.fromArray(new String[]{"A", "B"}),
//                Flux.just(1, 2, 3))
//                .subscribe(System.out::println);

        // map
//        Flux.just('A', 'B', 'C')
//                .map(a -> (int) (a))
//                .subscribe(System.out::println); // 65 66 67

        //  调度器 publishOn  subscribeOn
        // publishOn()方法切换的是操作符的执行方式，而 subscribeOn()方法切换的是产生流中元素时的执行方式。
        Flux.create(sink -> {
            sink.next(Thread.currentThread().getName());
            sink.complete();
        })
                .log()
                .publishOn(Schedulers.single())
                .map(x -> {
                    System.out.println("1:"+Thread.currentThread().getName()); // single-1 因为publishOn(Schedulers.single())
                    System.out.println("2:"+x); // 产生的序列 parallel-1，因为subscribeOn(Schedulers.parallel())
                    return String.format("[%s] %s", Thread.currentThread().getName(), x);
                })
                .publishOn(Schedulers.elastic())
                .map(x -> {
                    System.out.println("3:"+Thread.currentThread().getName()); // elastic-2
                    System.out.println("4:"+x); // x为上面的结果 [single-1] parallel-1
                    return String.format("[%s] %s", Thread.currentThread().getName(), x);
                })
                .subscribeOn(Schedulers.parallel())
                .toStream()
                .forEach(System.out::println);
        //1:single-1
        //2:parallel-1
        //3:elastic-2
        //4:[single-1] parallel-1
        //[elastic-2] [single-1] parallel-1






        try {
            Thread.currentThread().join(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }
}
