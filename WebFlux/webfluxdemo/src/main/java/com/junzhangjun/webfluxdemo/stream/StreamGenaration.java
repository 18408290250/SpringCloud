package com.junzhangjun.webfluxdemo.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 创建流
 */
public class StreamGenaration {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        // 从集合创建流
        list.stream();
        list.parallelStream();

        // 从数组创建流
        Arrays.stream(new int[]{1,2,3,4,5}).forEach(s-> System.out.println(s));
        System.out.println();

        // 创建数字流
        IntStream.of(1,2,3);
        System.out.println(IntStream.rangeClosed(1,10).count());
        System.out.println();

        // 使用Random创建一个无限流,创建无限流需要有限制条件，否则流不会停止
        new Random().ints().limit(10).forEach(System.out::println);

        // 自己创建无限流，同时给出限制条件
        Random random = new Random();
        Stream.generate(()->random.nextInt()).limit(20);
    }
}
