package com.junzhang.servicezpubsub.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 消息发布者
 */
@Service
public class SendService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void sendMessage(String message){
        try {
            stringRedisTemplate.convertAndSend("myChannel", message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
