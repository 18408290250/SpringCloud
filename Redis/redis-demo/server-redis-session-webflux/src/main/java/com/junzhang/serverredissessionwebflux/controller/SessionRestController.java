package com.junzhang.serverredissessionwebflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SessionRestController {


    // localhost:8080/getWebSession

//    @GetMapping("/getWebSession")
//    public Mono<Map<String, String>> getSession(
//            WebSession session) {
//        Map<String, String> map = new HashMap<>();
//        map.put("sessionId",session.getId());
//        map.put("note",(String) session.getAttributes().get("note"));
//
//        return Mono.just(map);
//    }


    @RequestMapping(value = "/login")
    public String login(WebSession session, String userName, String password)
    {
        String msg = "登录失败";
        if (userName!=null && "admin".equals(userName) && "111111".equals(password))
        {
            session.getAttributes().put("user", userName);
            msg = "登录成功，sessionId为："+ session.getId();
        }
        return msg;
    }

    @RequestMapping(value = "/index")
    public String index(WebSession session)
    {
        Object user = session.getAttribute("user");
        String msg = null;
        System.out.println(session.getId());
        if (user == null)
        {
            msg = "please login first";
        }else{
            msg = "index content,sessionId："+session.getId()+",user:"+user;
        }
        return msg;
    }



}
