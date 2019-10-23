package com.junzhang.zredistemplate.controller;

import com.junzhang.zredistemplate.entity.Student;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class List_RedisTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test0_setStr() {

        // Long leftPush(K key, V value);
        redisTemplate.opsForList().leftPush("list","java");
        redisTemplate.opsForList().leftPush("list","python");
        Long list = redisTemplate.opsForList().leftPush("list", "c++"); // [c++, python, java]

        // Long leftPushAll(K key, V... values);
        String[] a_str = new String[]{"1","2","3"};
        Long listarray = redisTemplate.opsForList().leftPushAll("listarray", a_str); // [3, 2, 1]

        // Long leftPushAll(K key, Collection<V> values);
        List<Object> strings = new ArrayList<Object>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        redisTemplate.opsForList().leftPushAll("listcollection", strings); // [3, 2, 1]

        // Long leftPushIfPresent(K key, V value);
        // 只有存在key对应的列表才能将这个value值插入到key所对应的列表中
        System.out.println(redisTemplate.opsForList().leftPushIfPresent("leftPushIfPresent","aa")); // 0  此时无leftPushIfPresent
        System.out.println(redisTemplate.opsForList().leftPushIfPresent("leftPushIfPresent","bb")); // 0  此时无leftPushIfPresent
        System.out.println("===========================================================");
        System.out.println(redisTemplate.opsForList().leftPush("leftPushIfPresent","aa")); // 1，因为list的长度为1; 此时leftPushIfPresent:[aa]
        System.out.println(redisTemplate.opsForList().leftPushIfPresent("leftPushIfPresent","bb")); // 1，因为list的长度为1; 此时leftPushIfPresent:[bb,aa]

        // Long leftPush(K key, V pivot, V value);
        // 把value值放到key对应列表中pivot值的左边，如果pivot值存在的话
        System.out.print(redisTemplate.opsForList().range("list",0,-1)); // [python, java]
        redisTemplate.opsForList().leftPush("list","java","oc");
        System.out.print(redisTemplate.opsForList().range("list",0,-1)); // [python, oc, java]

        // void trim(K key, long start, long end);
        // 修剪现有列表，使其只包含指定的指定范围的元素，起始和停止都是基于0的索引
        redisTemplate.opsForList().trim("list",1,-1);//裁剪第一个元素
        System.out.println(redisTemplate.opsForList().range("list",0,-1)); // [python, java]

        // Long size(K key)
        System.out.println(redisTemplate.opsForList().size("list")); // 2

        // void set(K key, long index, V value);
        System.out.println(redisTemplate.opsForList().range("list",0,-1)); // [python, oc, java]
        redisTemplate.opsForList().set("list",1,"setValue");
        System.out.println(redisTemplate.opsForList().range("list",0,-1)); // [python, setValue, java]

        // Long remove(K key, long count, Object value);
        List<Object> string = new ArrayList<Object>();
        string.add("a");
        string.add("b");
        string.add("c");
        string.add("a");
        string.add("b");
        string.add("c");
        redisTemplate.opsForList().leftPushAll("removelist", string);
        System.out.println(redisTemplate.opsForList().range("removelist",0,-1)); // [c, b, a, c, b, a]
        redisTemplate.opsForList().remove("removelist",1,"a");//count > 0  删掉从左边找 |count| 个等于value 的值   [c, b, c, b, a]
//        redisTemplate.opsForList().remove("removelist",-1,"a");//count < 0  删掉从右边找 |count| 个等于value 的值            [c, b, c, b]
//        redisTemplate.opsForList().remove("removelist",0,"c");//count = 0  删掉所有等于value 的值                            [b,b]
        System.out.println(redisTemplate.opsForList().range("removelist",0,-1));


        // V index(K key, long index);
        //  根据下表获取列表中的值，下标是从0开始的
        System.out.println(redisTemplate.opsForList().range("removelist",0,-1)); // [b,b]
        System.out.println(redisTemplate.opsForList().index("removelist",1));        // [b]

        // V leftPop(K key);
        // 弹出最左边的元素，弹出之后该值在列表中将不复存在
        System.out.println(redisTemplate.opsForList().range("list",0,-1));  // [python, setValue, java]
        System.out.println(redisTemplate.opsForList().leftPop("list"));           //  python
        System.out.println(redisTemplate.opsForList().range("list",0,-1)); //  [setValue, java]

        // V rightPopAndLeftPush(K sourceKey, K destinationKey)
        // 用于移除列表的最后一个元素，并将该元素添加到另一个列表并返回
        System.out.println(redisTemplate.opsForList().range("list",0,-1));  // [setValue, java]
        System.out.println(redisTemplate.opsForList().range("removelist",0,-1)); // [b, b]
        redisTemplate.opsForList().rightPopAndLeftPush("list","removelist");
        System.out.println(redisTemplate.opsForList().range("list",0,-1));     // [setValue]
        System.out.println(redisTemplate.opsForList().range("removelist",0,-1)); // [java, b, b]

    }

    @Test
    public void test(){
        System.out.println(redisTemplate.opsForList().range("list",0,-1));  // [setValue, java]
        System.out.println(redisTemplate.opsForList().range("removelist",0,-1)); // [b, b]
        redisTemplate.opsForList().rightPopAndLeftPush("list","removelist");
        System.out.println(redisTemplate.opsForList().range("list",0,-1));     // [setValue]
        System.out.println(redisTemplate.opsForList().range("removelist",0,-1)); // [java, b, b]
    }


    @Test
    public void test1_getStr() {
        redisTemplate.opsForList().leftPush("list","java");
        redisTemplate.opsForList().leftPush("list","python");
        Long list = redisTemplate.opsForList().leftPush("list", "c++"); // [c++, python, java]
    }

}
