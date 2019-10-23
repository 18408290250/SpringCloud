package com.junzhang.serviceredisredisson.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
// https://gitee.com/zhuyu1991/spring-cloud/blob/master/distributed-lock/src/main/java/com/zypcy/springcloud/distributedlock/DistributedLockApplication.java
public class TestLock {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private int num = 40;  // 票的总数

    private static String sticketCount = "count";//商品key
    private static String lockKey = "testRedisson";//分布式锁的key

    // localhost:8000/service7/testUnLock
    //  两个问题：
    // 1.并发问题 （重复）
    // 2.集群  不同的端口值一样  ->  两台机器各自库存都是 40，解决方式：redis存储 所有实例获取一个库存
//    @GetMapping("testUnLock")
//    public void testUnLock() {
//        String s = Thread.currentThread().getName();
//        if (num > 0) {
//            System.out.println(s + "购票成功，号码是：" + num);
//            num--;
//        } else {
//            System.out.println(s + "购票失败,号码已经被抢光");
//        }
//    }

    // 设置数量为40个
    // localhost:8000/service7/setValue?value=40
    @RequestMapping("/setValue")
    public String setValue(int value){
        stringRedisTemplate.opsForValue().set(sticketCount , value + "");
        return "success";
    }


    // 改用redis存储
    // 问题：超卖、重复
    // 理解：
    //      多个线程同时访问下面的代码：虽然redis是单线程，所有命令串行执行；但是这里客户端的并发导致串行的命令排列问题到达服务器与期望不一致，造成数据有问题
    //      一个线程里，有查，减，两个操作 :可能一个线程在get,另一个线程也正在get（取到相同的值） 或 一个set 另一个继续 set (超卖)
    // 事务 ？
    @GetMapping("testUnLock")
    public void testUnLock() throws InterruptedException {
        String s = Thread.currentThread().getName();
        if (Integer.valueOf(stringRedisTemplate.opsForValue().get(sticketCount)) > 0) {
            // 模拟延迟一秒出票
            TimeUnit.SECONDS.sleep(1);
            System.out.println(s + "购票成功，号码是：" + Integer.valueOf(stringRedisTemplate.opsForValue().get(sticketCount)));
            stringRedisTemplate.opsForValue().increment(sticketCount, -1);
        } else {
            System.out.println(s + "购票失败,票已经被抢光,剩余票："+stringRedisTemplate.opsForValue().get(sticketCount));
        }
    }


    @RequestMapping("/testLock")
    public void spike(){
        RLock lock = redissonClient.getLock(lockKey);
        String s = Thread.currentThread().getName();
        try{
            //lock.lockAsync(5 , TimeUnit.SECONDS);
            //lock.lock(5, TimeUnit.SECONDS); //设置5秒自动释放锁  （默认是30秒自动过期）

//            Future<Boolean> res = lock.tryLockAsync(100, 5, TimeUnit.SECONDS);
//            boolean result = res.get();
            boolean result = lock.tryLock(100, 5, TimeUnit.SECONDS);
            if(result){
                if (Integer.valueOf(stringRedisTemplate.opsForValue().get(sticketCount)) > 0) {
                    // 模拟延迟一秒出票
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(s + "购票成功，号码是：" + Integer.valueOf(stringRedisTemplate.opsForValue().get(sticketCount)));
                    stringRedisTemplate.opsForValue().increment(sticketCount, -1);
                } else {
                    System.out.println(s + "购票失败,票已经被抢光,剩余票："+stringRedisTemplate.opsForValue().get(sticketCount));
                }

            }else{
                System.out.println(s+"线程未抢到锁");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally{
            lock.unlock(); //释放锁
        }
    }

}
