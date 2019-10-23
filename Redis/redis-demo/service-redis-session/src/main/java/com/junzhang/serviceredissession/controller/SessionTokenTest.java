package com.junzhang.serviceredissession.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * https://blog.csdn.net/yyhcsfy/article/details/83145954
 */

//@RestController
public class SessionTokenTest {

    // localhost:8080/setSession

    // localhost:8080/getSession
    // localhost:8080/getSession
    // localhost:8080/login?userName=admin&password=111111

//    DefaultCookieSerializer



    @RequestMapping(value = "/setSession")
    public Map<String, Object> setSession(HttpServletRequest request,HttpSession session)
    {
        Map<String, Object> map = new HashMap<>();
        System.out.println(session.getId());
        session.setAttribute("message", request.getRequestURL());
        map.put("requestUrl", request.getRequestURL());
        map.put("sessionId",session.getId());
        return map;
    }


    // 禁用sesion后，getSession每一次都会变   =》 解决方案：在每次的请求request header添加 X-Auth-Token 及对应的值即可
    @RequestMapping(value = "/getSession")
    public Object getSession(HttpSession session)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", session.getId());
        System.out.println(session.getId());
        map.put("message", session.getAttribute("message"));
        return map;
    }


    @RequestMapping(value = "/login")
    public String login (HttpSession session, String userName, String password){
        String msg="logon failure!";

        if (userName!=null && "admin".equals(userName) && "111111".equals(password)){
            session.setAttribute("user",userName);
            msg="login successful! sessionId:"+session.getId();
        }
        System.out.println(session.getId());
        return msg;
    }

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
