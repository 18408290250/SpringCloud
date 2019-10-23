package com.junzhang.servicezpubsub.controller;


import com.junzhang.servicezpubsub.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

    @Autowired
    private SendService sendService;

    //  http://localhost:8011/send/hi
    //   http://localhost:8000/service6/send/hi
    @RequestMapping("/send/{message}")
    public void sendMessage(@PathVariable("message") String message){
        sendService.sendMessage(message);
    }

}
