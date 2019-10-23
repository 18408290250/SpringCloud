package com.junzhang.zredistemplate.controller;
import com.alibaba.fastjson.JSON;
import com.junzhang.zredistemplate.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTest {

//     RedisAutoConfiguration 自动声明了两个redis操作bean
//     JdkSerializationRedisSerializer
//     StringRedisSerializer

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void test5(){
        // redisTemplate 客户端不可读，但不影响程序使用
        redisTemplate.opsForValue().set("redisTemplate","redisTemplate");
        System.out.println(redisTemplate.opsForValue().get("redisTemplate")); // redisTemplate
    }

    @Test
    public void test6(){
        // stringRedisTemplate 以字符串存储
        stringRedisTemplate.opsForValue().set("stringRedisTemplate","stringRedisTemplate");
        System.out.println(stringRedisTemplate.opsForValue().get("stringRedisTemplate")); // stringRedisTemplate
    }

    @Test
    public void  test7(){
        // stringRedisTemplate 存的；redisTemplate 无法取
        System.out.println(redisTemplate.opsForValue().get("stringRedisTemplate")); // null

        // redisTemplate 存的；stringRedisTemplate 无法取
        System.out.println(stringRedisTemplate.opsForValue().get("redisTemplate")); // null
    }

    @Test
    public void  test8(){
        //  使用默认的redisTemplate 存对象，对象需可序列化 implements Serializable
        //  DefaultSerializer requires a Serializable payload but received an object of type
        Student s1 = new Student("张三","20181303002");
        redisTemplate.opsForValue().set("student",s1);
        // Student{name='张三', sno='20181303002'}
        System.out.println(redisTemplate.opsForValue().get("student"));
    }


    @Test
    public void  test9(){
        // 手动转化成json串再使用StringRedisSerializer存储
        Student s1 = new Student("张三","20181303002");
        String json = JSON.toJSON(s1).toString();
        stringRedisTemplate.opsForValue().set("student",json);
        // {"sno":"20181303002","name":"张三"}
        System.out.println(stringRedisTemplate.opsForValue().get("student"));
    }

}
