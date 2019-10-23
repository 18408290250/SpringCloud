package com.junzhang.serviceredissession.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@RestController
public class SessionTest {

    // localhost:8000/service1/login?userName=admin&password=111111
    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, String userName, String password)
    {
        String msg = "登录失败";
        if (userName!=null && "admin".equals(userName) && "111111".equals(password))
        {
            request.getSession().setAttribute("user", userName);
            request.getSession().setAttribute("message", request.getRequestURL());
            msg = request.getRequestURL()+"登录成功，sessionId为："+request.getSession().getId();
        }
        return msg;
    }

    // localhost:8000/service1/index
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
            msg = "index content,sessionId："+session.getId()+",user:"+user;
        }
        return msg;
    }

}
