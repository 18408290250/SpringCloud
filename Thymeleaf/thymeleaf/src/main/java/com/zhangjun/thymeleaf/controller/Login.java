package com.zhangjun.thymeleaf.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class Login {

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping("/user_login")
    @ResponseBody
    public String login(@RequestParam String username,@RequestParam String password, HttpServletRequest request){
        JSONObject obj = new JSONObject();
        int code = 0;
        String mes = null;
        HttpSession session = request.getSession();
        if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
            session.setAttribute("loginUser",username);
            code = 1;
            mes = "登录成功";
        }else{
            code = 0;
            mes = "登录失败";
        }
        obj.put("code",code);
        obj.put("message",mes);

        return obj.toString();
    }

}
