package com.junzhang.serviceredislock.controller;

import com.junzhang.serviceredislock.feign.ServiceTicket;
import org.omg.IOP.ServiceContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;



@RestController
public class RedisController {

   private final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    private static String sticketCount = "store";//商品key

    @Autowired
    ServiceTicket serviceTicket;


    // 设置数量为40个
    // localhost:8000/service2/setValue?value=40
    @RequestMapping("/setValue")
    public String setValue(int value){
        stringRedisTemplate.opsForValue().set(sticketCount, value + "");
        return "success";
    }


    //tryLock() 等待时间问题：
    // 若 tryLock(1, TimeUnit.SECONDS) 且 在出票前模拟延迟一秒，存在问题：很多线程拿不到锁
    // 第一个线程拿到锁后，一秒+ 之后释放，此时后面的线程(等待时间为1s),则都拿不到锁，直接走了else
    @GetMapping("testLock")
    public void testLock() throws InterruptedException {
        //通过feign 调用远程票的信息  http://localhost:8000/service5/hello
        String ticketInfo =  serviceTicket.getTiketInfo();
        Lock lock = redisLockRegistry.obtain("lock");
        boolean isLock = lock.tryLock(100, TimeUnit.SECONDS);  // 改为 100
        String s = Thread.currentThread().getName();
        try {
            if(isLock) {
                if (Integer.valueOf(stringRedisTemplate.opsForValue().get(sticketCount)) > 0) {
                    // 模拟延迟一秒出票
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(s + "购票成功，号码是：" + Integer.valueOf(stringRedisTemplate.opsForValue().get(sticketCount))+"\n 详情："+ ticketInfo);

                    stringRedisTemplate.opsForValue().increment(sticketCount, -1);
                } else {
                    System.out.println(s + "购票失败,票已经被抢光,剩余票："+stringRedisTemplate.opsForValue().get(sticketCount));
                }
            }else{
                System.out.println(s + "未拿到锁");
            }
        }catch (Exception e){}
        finally {
            if(isLock) {
                lock.unlock();
            }
        }
    }





    // localhost:8000/service2/index
    @RequestMapping(value = "/index")
    public String index(HttpSession session)
    {
        Object user = session.getAttribute("user");
        String msg = null;
        System.out.println(session.getId());
        if (user == null)
        {
            msg = "please login first";
        }else{
            msg = "index content,sessionId:"+session.getId()+",user:"+user;
        }
        return msg;
    }


}
