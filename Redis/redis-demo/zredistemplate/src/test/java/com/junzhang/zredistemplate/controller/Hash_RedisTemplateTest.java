package com.junzhang.zredistemplate.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Hash_RedisTemplateTest {


    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test0_setStr() {

        // void put(H key, HK hashKey, HV value);
        redisTemplate.opsForHash().put("redisHash","name","nick");
        redisTemplate.opsForHash().put("redisHash","name","tom");
        redisTemplate.opsForHash().put("redisHash","age","26");
        redisTemplate.opsForHash().put("redisHash","class","6");

        // void putAll(H key, Map<? extends HK, ? extends HV> m);
        Map<String,Object> testMap = new HashMap();
        testMap.put("name","jack");
        testMap.put("age","27");
        testMap.put("class","1");
        redisTemplate.opsForHash().putAll("redisHash1",testMap);

        // Boolean putIfAbsent(H key, HK hashKey, HV value);
        // 仅当hashKey不存在时才设置散列hashKey的值
        System.out.println(redisTemplate.opsForHash().putIfAbsent("redisHash","age","30"));
        System.out.println(redisTemplate.opsForHash().putIfAbsent("redisHash","kkk","kkk"));

        // HV get(H key, Object hashKey);
        System.out.println(redisTemplate.opsForHash().get("redisHash","age"));  // 26

        // List<HV> multiGet(H key, Collection<HK> hashKeys);
        // 从哈希中获取给定hashKey的值
        List<Object> kes = new ArrayList<Object>();
        kes.add("name");
        kes.add("age");
        System.out.println(redisTemplate.opsForHash().multiGet("redisHash",kes));  // [tom, 26]

        // Set<HK> keys(H key);
        // 获取key所对应的散列表的key
        System.out.println(redisTemplate.opsForHash().keys("redisHash")); // [name, age, class, kkk]

        // List<HV> values(H key);
        // 获取整个哈希存储的值根据密钥
        System.out.println(redisTemplate.opsForHash().values("redisHash")); // [tom, 26, 6, kkk]

        // Map<HK, HV> entries(H key);
        // 获取整个哈希存储根据密钥
        System.out.println(redisTemplate.opsForHash().entries("redisHash")); // {name=tom, age=26, class=6, kkk=kkk}

        // Cursor<Map.Entry<HK, HV>> scan(H key, ScanOptions options);
        // 使用Cursor在key的hash中迭代，相当于迭代器。
        Cursor<Map.Entry<Object, Object>> curosr = redisTemplate.opsForHash().scan("redisHash", ScanOptions.NONE);
        while(curosr.hasNext()){
            Map.Entry<Object, Object> entry = curosr.next();
            System.out.println(entry.getKey()+":"+entry.getValue());
            // name:tom
            //age:26
            //class:6
            //kkk:kkk
        }


        // Long size(H key);
        // 获取key所对应的散列表的大小个数
        System.out.println(redisTemplate.opsForHash().size("redisHash")); // 4

        // Long delete(H key, Object... hashKeys);
        System.out.println(redisTemplate.opsForHash().delete("redisHash","kkk")); // 1
        System.out.println(redisTemplate.opsForHash().entries("redisHash"));    // {name=tom, age=26, class=6}

        // Boolean hasKey(H key, Object hashKey);
        System.out.println(redisTemplate.opsForHash().hasKey("redisHash","age")); // true
        System.out.println(redisTemplate.opsForHash().hasKey("redisHash","kkk")); // false

        // Long increment(H key, HK hashKey, long delta);
        // 通过给定的delta增加散列hashKey的值（整型）
        System.out.println(redisTemplate.opsForHash().get("redisHash","age")); // 26
        System.out.println(redisTemplate.opsForHash().increment("redisHash","age",1)); // 27

        // Double increment(H key, HK hashKey, double delta);
        // 通过给定的delta增加散列hashKey的值（浮点数）
        System.out.println(redisTemplate.opsForHash().get("redisHash","age")); // 27
        System.out.println(redisTemplate.opsForHash().increment("redisHash","age",1.1)); // 28.1
    }

    @Test
    public void test(){
        System.out.println(redisTemplate.opsForHash().get("redisHash","name"));
        redisTemplate.opsForHash().put("redisHash","name","nick");
        redisTemplate.opsForHash().put("redisHash","name","tom");
        System.out.println(redisTemplate.opsForHash().get("redisHash","name"));
    }

}
