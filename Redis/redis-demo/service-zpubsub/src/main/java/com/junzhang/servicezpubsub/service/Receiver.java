package com.junzhang.servicezpubsub.service;

import org.springframework.stereotype.Component;

/**
 * 消息订阅者
 */
@Component
public class Receiver {

    public void receiveMessage(String message){
        System.out.println("Receive: " + message);
    }
}
