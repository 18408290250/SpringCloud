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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Set_RedisTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test0_setStr() {

        // Long add(K key, V... values);
        // 无序集合中添加元素，返回添加个数
        // 也可以直接在add里面添加多个值
        redisTemplate.opsForSet().add("setTest","aaa","bbb");
        String[] strarrays = new String[]{"strarr1","starr2"};
        System.out.println(redisTemplate.opsForSet().add("setTest", strarrays)); // 2

        // Set<V> members(K key);
        // 返回集合中的所有成员
        System.out.println(redisTemplate.opsForSet().members("setTest")); // [aaa, strarr1, starr2, bbb]


        // Boolean isMember(K key, Object o);
        // 判断 member 元素是否是集合 key 的成员
        System.out.println(redisTemplate.opsForSet().isMember("setTest","ccc")); // false
        System.out.println(redisTemplate.opsForSet().isMember("setTest","aaa")); // true

        // Long remove(K key, Object... values);
        // 移除集合中一个或多个成员,返回移除的元素个数
        String[] strarray = new String[]{"strarr1","starr2"};
        System.out.println(redisTemplate.opsForSet().remove("setTest",strarray)); // 2

        // V pop(K key);
        // 移除并返回集合中的一个随机元素
        System.out.println(redisTemplate.opsForSet().pop("setTest")); // aaa
        System.out.println(redisTemplate.opsForSet().members("setTest")); // [bbb]

        // Long size(K key);
        System.out.println(redisTemplate.opsForSet().size("setTest")); // 1

        // Boolean move(K key, V value, K destKey);
        // 将 member 元素从 source 集合移动到 destination 集合 注：key:destination必须要先存在
        redisTemplate.opsForSet().add("setTest2","aaa","bbb");
        redisTemplate.opsForSet().move("setTest","bbb","setTest2");
        System.out.println(redisTemplate.opsForSet().members("setTest")); // []
        System.out.println(redisTemplate.opsForSet().members("setTest2"));// [aaa, bbb]

        // Set<V> intersect(K key, K otherKey);
        // key对应的无序集合与otherKey对应的无序集合求交集
        System.out.println(redisTemplate.opsForSet().intersect("setTest","setTest2")); // []

        // Set<V> intersect(K key, Collection<K> otherKeys);
        // key对应的无序集合与多个otherKey对应的无序集合求交集
        redisTemplate.opsForSet().add("setTest","aaa");
        redisTemplate.opsForSet().add("setTest3","aaa","bbb");
        List<String> strlist = new ArrayList<String>();
        strlist.add("setTest2");
        strlist.add("setTest3");
        System.out.println(redisTemplate.opsForSet().intersect("setTest",strlist)); // [aaa]

        // Long intersectAndStore(K key, K otherKey, K destKey);
        // key无序集合与otherkey无序集合的交集存储到destKey无序集合中 注：destKey 可以先不存在
        System.out.println(redisTemplate.opsForSet().members("setTest"));// [aaa]
        System.out.println(redisTemplate.opsForSet().members("setTest2")); // [aaa, bbb]
        System.out.println(redisTemplate.opsForSet().intersectAndStore("setTest","setTest2","destKey1"));
        System.out.println(redisTemplate.opsForSet().members("destKey1")); // [aaa]

        // Set<V> union(K key, K otherKey);
        // key无序集合与otherKey无序集合的并集
        System.out.println(redisTemplate.opsForSet().union("setTest","setTest2")); // [aaa, bbb]

        // Long unionAndStore(K key, K otherKey, K destKey);
        // key无序集合与otherkey无序集合的并集存储到destKey无序集合中
        System.out.println(redisTemplate.opsForSet().members("setTest")); //  [aaa]
        System.out.println(redisTemplate.opsForSet().members("setTest2")); // [aaa, bbb]
        System.out.println(redisTemplate.opsForSet().unionAndStore("setTest","setTest2","unionAndStoreTest")); // 2
        System.out.println(redisTemplate.opsForSet().members("unionAndStoreTest")); // [aaa, bbb]

        // Set<V> difference(K key, K otherKey);
        // key无序集合与otherKey无序集合的差集  (并集 - 交集) -> 注： 第一个有，第二个没有
        System.out.println(redisTemplate.opsForSet().members("setTest")); // [aaa]
        System.out.println(redisTemplate.opsForSet().members("setTest2"));// [aaa, bbb]
        System.out.println(redisTemplate.opsForSet().difference("setTest2","setTest")); // [bbb]
        System.out.println(redisTemplate.opsForSet().difference("setTest","setTest2")); // []

        // Long differenceAndStore(K key, K otherKey, K destKey);
        // key无序集合与otherkey无序集合的差集存储到destKey无序集合中
        System.out.println(redisTemplate.opsForSet().members("setTest")); // [aaa]
        System.out.println(redisTemplate.opsForSet().members("setTest2"));// [aaa, bbb]
        System.out.println(redisTemplate.opsForSet().differenceAndStore("setTest2","setTest","differenceAndStore")); // 1
        System.out.println(redisTemplate.opsForSet().members("differenceAndStore")); // [bbb]

        // V randomMember(K key);
        // 随机获取key无序集合中的一个元素
        System.out.println(redisTemplate.opsForSet().randomMember("setTest")); // bbb
        System.out.println(redisTemplate.opsForSet().randomMember("setTest")); // aaa


        // List<V> randomMembers(K key, long count);
        // 随机获取多个key无序集合中的元素，count表示个数
        redisTemplate.delete("setTest");
        redisTemplate.opsForSet().add("setTest", "a","b","c","d","e");
        System.out.println(redisTemplate.opsForSet().randomMembers("setTest",8));//[c, e, e, a, c, b, c, e]
        System.out.println(redisTemplate.opsForSet().randomMembers("setTest",4));//[c, c, b, e]

        // Set<V> distinctRandomMembers(K key, long count);
        // 获取多个key无序集合中的元素（无重复），count表示个数
        System.out.println(redisTemplate.opsForSet().distinctRandomMembers("setTest",6));//[a, e, b, c, d]
        System.out.println(redisTemplate.opsForSet().distinctRandomMembers("setTest",4));// [d, a, c, e]

        // Cursor<V> scan(K key, ScanOptions options);
        Cursor<Object> curosr = redisTemplate.opsForSet().scan("setTest", ScanOptions.NONE);
        while(curosr.hasNext()){
            System.out.println(curosr.next());
            //a
            //d
            //c
            //e
            //b
        }
    }

    @Test
    public void test(){
        redisTemplate.opsForSet().add("setTest","aaa","bbb");
        String[] strarrays = new String[]{"strarr1","starr2"};
        System.out.println(redisTemplate.opsForSet().add("setTest", strarrays));
    }
}
