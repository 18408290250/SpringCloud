package com.zhangjun.thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;

@Controller
public class I18n {

//    @Autowired
//    LocaleResolver localeResolver;
//
    @RequestMapping("i18n")
    public String test(@Autowired HttpServletRequest request,@Autowired HttpServletResponse response) {
//        HttpSession session=request.getSession();
//        localeResolver.setLocale(request,response, Locale.ENGLISH);
//        System.out.println(session.getAttribute(LOCALE_SESSION_ATTRIBUTE_NAME));


        return "i18n";
    }
}
