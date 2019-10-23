package com.junzhangjun.webfluxdemo.controllers;

import com.junzhangjun.webfluxdemo.domain.User;
import com.junzhangjun.webfluxdemo.service.UserService;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {

    public static Map<String, User> dataMap = new ConcurrentHashMap<>();
    static {
        dataMap.put("1", new User("aa","m"));
        dataMap.put("2", new User("bb","f"));
        dataMap.put("3", new User("cc","m"));
        dataMap.put("4", new User("dd","m"));
        dataMap.put("5", new User("ee","m"));
        dataMap.put("6", new User("ff","m"));
        dataMap.put("7", new User("gg","m"));
        dataMap.put("8", new User("hh","f"));
        dataMap.put("9", new User("ii","f"));
    }

    @org.junit.Test
    public void test(){
        Stream<User> userStream = UserService.dataMap.entrySet().stream()
                .filter(Objects::nonNull)
//                .peek(x -> System.out.println(x.getKey()))
                .filter(x -> x.getKey().equals("2"))
//                .peek(x -> System.out.println(x.toString()))
                .map(y -> y.getValue());

        userStream
                .forEach(System.out::println);

//        UserService.dataMap.entrySet().stream().forEach(x -> x.getKey());

    }

    @org.junit.Test
    public void test2(){
        List<? extends Serializable> collect = Stream.of(getStr(), getSum())
                .map(Stream::of)
                .flatMap(x -> x)
                .peek(x -> System.out.println("z:"+x))
                .collect(Collectors.toList());
    }

    @org.junit.Test
    public void testZip(){
        Mono<String> stringMono = Mono.fromSupplier(() -> getStr());
        Mono<Integer> integerMono = Mono.fromSupplier(() -> getSum());
        Mono.zip(items -> {
            String cast = String.class.cast(items[0]);
            Integer cast1 = Integer.class.cast(items[1]);
            // Do merge
            List<? extends Serializable> collect = Stream.of(cast, cast1)
                    .map(Stream::of)
                    .flatMap(x -> x)
                    .peek(x -> System.out.println("z:"+x))
                    .collect(Collectors.toList());
            return collect;
        }, stringMono, integerMono)
        .subscribe(System.out::println);
    }

    public String getStr(){
        List<String> foo = Stream.generate(() -> "foo")
                .limit(100)
                .peek(x -> System.out.println("x="+x))
                .collect(Collectors.toList());
        return foo.toString();
    }
    public Integer getSum(){
        int reduce = IntStream.range(0, 100)
                .peek(x -> System.out.println("y="+x))
                .reduce(0, (x, y) -> x + y);
        return  reduce;
    }


}


