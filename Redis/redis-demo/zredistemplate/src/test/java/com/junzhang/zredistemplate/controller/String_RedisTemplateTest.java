package com.junzhang.zredistemplate.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class String_RedisTemplateTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test0_setStr() {

        // set void set(K key, V value)
        stringRedisTemplate.opsForValue().set("name","StringRedisTemplate");

        // get V get(Object key);
        stringRedisTemplate.opsForValue().get("name");  // StringRedisTemplate

        // get String get(K key, long start, long end);
        // 截取key所对应的value字符串
        stringRedisTemplate.opsForValue().set("name","StringRedisTemplate");
        System.out.println(stringRedisTemplate.opsForValue().get("name")); // StringRedisTemplate
        System.out.println(stringRedisTemplate.opsForValue().get("name",0,5));// String
        System.out.println(stringRedisTemplate.opsForValue().get("name",0,-1));// StringRedisTemplate
        System.out.println(stringRedisTemplate.opsForValue().get("name",-3,-1));// ate

        // set void set(K key, V value, long timeout, TimeUnit unit);
        // 设置10秒失效，十秒之内查询有结果，十秒之后返回为null
        stringRedisTemplate.opsForValue().set("name","tom",10, TimeUnit.SECONDS);

        // set void set(K key, V value, long offset);
        // 该方法是用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始
        stringRedisTemplate.opsForValue().set("key","helloworld");
        stringRedisTemplate.opsForValue().set("key","redis", 2);
        System.out.println(stringRedisTemplate.opsForValue().get("key")); // heredisrld

        // Boolean setIfAbsent(K key, V value)
        stringRedisTemplate.opsForValue().setIfAbsent("name","nick");
        System.out.println(stringRedisTemplate.opsForValue().get("name")); // StringRedisTemplate


        // multiSet void multiSet(Map<? extends K, ? extends V> m);
        // 为多个键分别设置它们的值
        Map<String,String> maps = new HashMap<String, String>();
        maps.put("multi1","multi1");
        maps.put("multi2","multi2");
        maps.put("multi3","multi3");
        stringRedisTemplate.opsForValue().multiSet(maps);

        // multiGet List<V> multiGet(Collection<K> keys);
        List<String> keys = new ArrayList<String>();
        keys.add("multi1");
        keys.add("multi2");
        keys.add("multi3");
        System.out.println(stringRedisTemplate.opsForValue().multiGet(keys)); // [multi1, multi2, multi3]

        //  Boolean multiSetIfAbsent(Map<? extends K, ? extends V> m);
        Map<String,String> maps1 = new HashMap<String, String>();
        maps1.put("multi11","multi111");
        maps1.put("multi22","multi22");
        maps1.put("multi33","multi33");
        Map<String,String> maps2 = new HashMap<String, String>();
        maps2.put("multi1","multi1");
        maps2.put("multi2","multi2");
        maps2.put("multi3","multi3");
        System.out.println(stringRedisTemplate.opsForValue().multiSetIfAbsent(maps1)); // true，同时set进去
        System.out.println(stringRedisTemplate.opsForValue().multiSetIfAbsent(maps2));// false，不更新

        // getAndSet V getAndSet(K key, V value);
        // 设置键的字符串值并返回其旧值
        stringRedisTemplate.opsForValue().set("getSetTest","test");
        System.out.println(stringRedisTemplate.opsForValue().getAndSet("getSetTest","test2")); // test
        System.out.println(stringRedisTemplate.opsForValue().get("getSetTest")); // test2


        // Long increment(K key, long delta);
        // 获取指定key的值进行加1，如果value不是integer类型，会抛异常，如果key不存在会创建一个，默认value为0
        Long increlong = stringRedisTemplate.opsForValue().increment("increlong", 1);
        System.out.println(increlong); // 1

        // Double increment(K key, double delta);
        Double increlong1 = stringRedisTemplate.opsForValue().increment("increlong", 1.2);
        System.out.println(increlong1); // 2.2

        // append Integer append(K key, String value);
        // 如果key已经存在并且是一个字符串，则该命令将该值追加到字符串的末尾。如果键不存在，则它被创建并设置为空字符串，因此APPEND在这种特殊情况下将类似于SET
        System.out.println(stringRedisTemplate.opsForValue().get("appendTest")); // null
        stringRedisTemplate.opsForValue().append("appendTest","hello");
        System.out.println(stringRedisTemplate.opsForValue().append("appendTest","hello")); // hellohello


        // size Long size(K key);
        // 返回key所对应的value值得长度
        System.out.println(stringRedisTemplate.opsForValue().size("name")); // 19  StringRedisTemplate
    }



    @Test
    public void test() {
        System.out.println(stringRedisTemplate.opsForValue().size("name"));
    }

    @Test
    public void  test2(){

    }

    @Test
    public void test2_del() {
        stringRedisTemplate.delete("appendTest");
    }


    @Test
    public void test3(){
//        stringRedisTemplate.opsForValue().set("redistemplate","redistemplate");
//        System.out.println(stringRedisTemplate.opsForValue().get("redistemplate"));

     //   stringRedisTemplate.opsForValue().set("stringRedisTemplate","stringRedisTemplate");
        System.out.println(stringRedisTemplate.opsForValue().get("redistemplate_b")); // null

    }

}