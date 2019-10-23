package com.junzhang.zredistemplate.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZSet_RedisTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test0_setStr() {

        // Boolean add(K key, V value, double score);
        // 新增一个有序集合，存在的话为false，不存在的话为true
        System.out.println(redisTemplate.opsForZSet().add("zset1","zset-1",1.0)); // true

        // Long add(K key, Set<TypedTuple<V>> tuples);
        // 新增一个有序集合,返回新增元素个数
        ZSetOperations.TypedTuple<Object> objectTypedTuple1 = new DefaultTypedTuple<Object>("zset-5",9.6);
        ZSetOperations.TypedTuple<Object> objectTypedTuple2 = new DefaultTypedTuple<Object>("zset-6",9.9);
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<ZSetOperations.TypedTuple<Object>>();
        tuples.add(objectTypedTuple1);
        tuples.add(objectTypedTuple2);
        System.out.println(redisTemplate.opsForZSet().add("zset1",tuples)); // 2
        System.out.println(redisTemplate.opsForZSet().range("zset1",0,-1)); // [zset-1, zset-5, zset-6]

        // Set<V> range(K key, long start, long end);
        // 通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
        System.out.println(redisTemplate.opsForZSet().range("zset1",0,-1)); // [zset-1, zset-5, zset-6]

        // Set<TypedTuple<V>> rangeWithScores(K key, long start, long end);  >= start && <= end
        // 通过索引区间返回有序集合成指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
        Set<ZSetOperations.TypedTuple<Object>> tuples1 = redisTemplate.opsForZSet().rangeWithScores("zset1",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = tuples1.iterator();
        while (iterator.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            System.out.println("value:" + typedTuple.getValue() + "；score:" + typedTuple.getScore());
            //value:zset-1；score:1.0
            //value:zset-5；score:9.6
            //value:zset-6；score:9.9
        }

        // Set<V> rangeByScore(K key, double min, double max);
        // 通过分数返回有序集合指定区间内的成员，其中有序集成员按分数值递增(从小到大)顺序排列
        System.out.println(redisTemplate.opsForZSet().rangeByScore("zset1",0,5)); // [zset-1]

        // Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max);
        // 通过分数返回有序集合指定区间内的成员对象，其中有序集成员按分数值递增(从小到大)顺序排列
        Set<ZSetOperations.TypedTuple<Object>> tuples2 = redisTemplate.opsForZSet().rangeByScoreWithScores("zset1",0,5);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator1 = tuples2.iterator();
        while (iterator1.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator1.next();
            System.out.println("value:" + typedTuple.getValue() + "；score:" + typedTuple.getScore()); // value:zset-1；score:1.0
        }

        // Set<V> rangeByScore(K key, double min, double max, long offset, long count);
        // 通过分数返回有序集合指定区间内的成员，并在索引范围内，其中有序集成员按分数值递增(从小到大)顺序排列
        redisTemplate.opsForZSet().add("zset1","set-2",1.2);
        redisTemplate.opsForZSet().add("zset1","zset-3",2.3);
        redisTemplate.opsForZSet().add("zset1","zset-4",6.6);
        System.out.println(redisTemplate.opsForZSet().rangeByScore("zset1",0,5)); // [zset-1, set-2, zset-3]
        System.out.println(redisTemplate.opsForZSet().rangeByScore("zset1",0,5,2,1)); // [zset-3]


        // Set<TypedTuple<V>> rangeByScoreWithScores(K key, double min, double max, long offset, long count);
        // 通过分数返回有序集合指定区间内的成员对象，并在索引范围内，其中有序集成员按分数值递增(从小到大)顺序排列
        Set<ZSetOperations.TypedTuple<Object>> tuples3 = redisTemplate.opsForZSet().rangeByScoreWithScores("zset1", 0, 5, 1, 2);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator3 = tuples3.iterator();
        while (iterator3.hasNext()) {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator3.next();
            System.out.println("value:" + typedTuple.getValue() + "；score:" + typedTuple.getScore());
            // value:set-2；score:1.2
            // value:zset-3；score:2.3
        }

        // Set<V> reverseRange(K key, long start, long end);
        // 通过索引区间返回有序集合成指定区间内的成员，其中有序集成员按分数值递减(从大到小)顺序排列
        System.out.println(redisTemplate.opsForZSet().reverseRange("zset1",0,-1)); // [zset-6, zset-5, zset-4, zset-3, set-2, zset-1]

        // Long rank(K key, Object o);
        // 返回有序集中指定成员的排名，其中有序集成员按分数值递增(从小到大)顺序排列
        System.out.println(redisTemplate.opsForZSet().range("zset1",0,-1)); // [zset-1, set-2, zset-3, zset-4, zset-5, zset-6]
        System.out.println(redisTemplate.opsForZSet().rank("zset1","zset-3")); // 2
        System.out.println(redisTemplate.opsForZSet().rank("zset1","zset-7")); // null  若无该Object，返回null

        // Long reverseRank(K key, Object o);
        // 返回有序集中指定成员的排名，其中有序集成员按分数值递减(从大到小)顺序排列
        System.out.println(redisTemplate.opsForZSet().range("zset1",0,-1)); // [zset-1, set-2, zset-3, zset-4, zset-5, zset-6]
        System.out.println(redisTemplate.opsForZSet().reverseRank("zset1","zset-3")); // 3
        System.out.println(redisTemplate.opsForZSet().reverseRank("zset1","zset-7")); // null  若无该Object，返回null

        // Long count(K key, double min, double max);
        // 通过分数返回有序集合指定区间内的成员个数
        System.out.println(redisTemplate.opsForZSet().rangeByScore("zset1",0,5)); // [zset-1, set-2, zset-3]
        System.out.println(redisTemplate.opsForZSet().count("zset1",0,5)); // 3

        // Long size(K key);
        // 获取有序集合的成员数，内部调用的就是zCard方法
        System.out.println(redisTemplate.opsForZSet().size("zset1")); // 6

        // Long zCard(K key);
        // 获取有序集合的成员数
        System.out.println(redisTemplate.opsForZSet().zCard("zset1")); // 6

        // Double score(K key, Object o);
        // 获取指定成员的score值
        System.out.println(redisTemplate.opsForZSet().score("zset1","zset-1")); // 1.0

        // Long remove(K key, Object... values);
        // 从有序集合中移除一个或者多个元素
        System.out.println(redisTemplate.opsForZSet().range("zset1",0,-1)); // [zset-1, set-2, zset-3, zset-4, zset-5, zset-6]
        System.out.println(redisTemplate.opsForZSet().remove("zset1","zset-6")); // 1
        System.out.println(redisTemplate.opsForZSet().range("zset1",0,-1)); // [zset-1, set-2, zset-3, zset-4, zset-5]

        // Long removeRange(K key, long start, long end);  return: 删除的元素个数
        // 移除指定索引位置的成员，其中有序集成员按分数值递增(从小到大)顺序排列
        System.out.println(redisTemplate.opsForZSet().add("zset2","zset-1",1.1)); // true
        System.out.println(redisTemplate.opsForZSet().add("zset2","zset-2",1.2)); // true
        System.out.println(redisTemplate.opsForZSet().add("zset2","zset-3",2.3)); // true
        System.out.println(redisTemplate.opsForZSet().add("zset2","zset-4",6.6)); // true

        System.out.println(redisTemplate.opsForZSet().range("zset2",0,-1)); // [zset-1, zset-2, zset-3, zset-4]
        System.out.println(redisTemplate.opsForZSet().removeRange("zset2",1,2)); // 2
        System.out.println(redisTemplate.opsForZSet().range("zset2",0,-1)); // [zset-1, zset-4]

        // Long removeRangeByScore(K key, double min, double max);
        // 根据指定的score值得范围来移除成员
        System.out.println(redisTemplate.opsForZSet().range("zset2",0,-1)); // [zset-1, zset-4]
        System.out.println(redisTemplate.opsForZSet().removeRangeByScore("zset2",1,3)); // 1
        System.out.println(redisTemplate.opsForZSet().range("zset2",0,-1)); //  [zset-4]

        // Long unionAndStore(K key, K otherKey, K destKey);
        // 计算给定的一个有序集的并集，并存储在新的 destKey中，key相同的话会把score值相加
        System.out.println(redisTemplate.opsForZSet().add("zzset1","zset-1",1.0));
        System.out.println(redisTemplate.opsForZSet().add("zzset1","zset-2",2.0));
        System.out.println(redisTemplate.opsForZSet().add("zzset1","zset-3",3.0));
        System.out.println(redisTemplate.opsForZSet().add("zzset1","zset-4",6.0));

        System.out.println(redisTemplate.opsForZSet().add("zzset2","zset-1",1.0));
        System.out.println(redisTemplate.opsForZSet().add("zzset2","zset-2",2.0));
        System.out.println(redisTemplate.opsForZSet().add("zzset2","zset-3",3.0));
        System.out.println(redisTemplate.opsForZSet().add("zzset2","zset-4",6.0));
        System.out.println(redisTemplate.opsForZSet().add("zzset2","zset-5",7.0));
        System.out.println(redisTemplate.opsForZSet().unionAndStore("zzset1","zzset2","destZset11")); // 5

        Set<ZSetOperations.TypedTuple<Object>> tuples4 = redisTemplate.opsForZSet().rangeWithScores("destZset11",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator2 = tuples4.iterator();
        while (iterator2.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator2.next();
            System.out.println("value:" + typedTuple.getValue() + "；score:" + typedTuple.getScore());
            // value:zset-1；score:2.0
            //value:zset-2；score:4.0
            //value:zset-3；score:6.0
            //value:zset-5；score:7.0
            //value:zset-4；score:12.0
        }

        // Long intersectAndStore(K key, K otherKey, K destKey);
        // 计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
        System.out.println(redisTemplate.opsForZSet().intersectAndStore("zzset1","zzset2","destZset33")); // 4
        Set<ZSetOperations.TypedTuple<Object>> tuples5 = redisTemplate.opsForZSet().rangeWithScores("destZset33",0,-1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator4 = tuples5.iterator();
        while (iterator4.hasNext())
        {
            ZSetOperations.TypedTuple<Object> typedTuple = iterator4.next();
            System.out.println("value:" + typedTuple.getValue() + "score:" + typedTuple.getScore());
            // value:zset-1score:2.0
            //value:zset-2score:4.0
            //value:zset-3score:6.0
            //value:zset-4score:12.0
        }


        // Cursor<TypedTuple<V>> scan(K key, ScanOptions options);
        // 遍历zset
        Cursor<ZSetOperations.TypedTuple<Object>> cursor = redisTemplate.opsForZSet().scan("zzset1", ScanOptions.NONE);
        while (cursor.hasNext()){
            ZSetOperations.TypedTuple<Object> item = cursor.next();
            System.out.println(item.getValue() + ":" + item.getScore());
            // zset-1:1.0
            //zset-2:2.0
            //zset-3:3.0
            //zset-4:6.0
        }

        // Double incrementScore(K key, V value, double delta);
        // 增加元素的score值，并返回增加后的值
        System.out.println(redisTemplate.opsForZSet().score("zset1","zset-1")); // 1.0
        System.out.println(redisTemplate.opsForZSet().incrementScore("zset1","zset-1",1.1));  // 2.1
        System.out.println(redisTemplate.opsForZSet().score("zset1","zset-1")); // 2.1

    }

    @Test
    public void test() {
        System.out.println(redisTemplate.opsForZSet().score("zset1","zset-1")); // 1.0
        System.out.println(redisTemplate.opsForZSet().incrementScore("zset1","zset-1",1.1));  // 2.1
        System.out.println(redisTemplate.opsForZSet().score("zset1","zset-1")); // 2.1
    }
}
