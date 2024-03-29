package com.junzhangjun.webfluxdemo.stream;

import java.util.Random;
import java.util.stream.Stream;

/**
 * 流的中间操作
 */
public class StreamIntermediateOp {
    public static void main(String[] args) {
        String str = "my name is 007";

        // 把每个单词的长度打印出来
        Stream.of(str.split(" ")).filter(s -> s.length() > 2)
                .map(s -> s.length()).forEach(System.out::println);
        System.out.println();

        // flatMap A->B属性(B属性是个集合),最终得到所有的A元素里面的所有B属性集合
        // intStream/longStream 并不是Stream的子类，所以需要装箱操作boxed;
        Stream.of(str.split(" ")).flatMap(s -> s.chars().boxed())
                .forEach(i -> System.out.println((char) i.intValue()));
        System.out.println();

        // peek是个中间操作常用于debug，forEach是个终止操作；
        System.out.println("--------------peek------------");
        Stream.of(str.split(" ")).peek(System.out::println)
                .forEach(System.out::println);

        // limit的使用，主要用于对无限流的限制操作
        new Random().ints().filter(i -> i > 100 && i < 1000).limit(10)
                .forEach(System.out::println);
    }
}
