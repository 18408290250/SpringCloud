package com.junzhang.servicerediscache.controller;

import com.junzhang.servicerediscache.service.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;


@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserService userService;

    // http://localhost:8000/service4/testNoParam
    // 默认无参数，存入的键为 testNoParam::SimpleKey []
    @RequestMapping(value = "/testNoParam")
    @Cacheable(value = "testNoParam")
    public String testNoParam()
    {
        return "hello";
    }

    // localhost:8000/service4/getUserByName?userName=admin
    @RequestMapping("/getUserByName")
    @Cacheable(value = "userCache", key = "#userName") // @Cacheable(value = "userCache")
    // @Cacheable(value ="userCache") 当调用这个方法的时候，会从一个key为userCache:id的缓存中查询，
    // 如果没有，则执行实际的方法，并将返回的结果存入缓存中；否则返回缓存中的对象
    // 当重复使用相同参数调用方法的时候,方法本身不会被调用执行,即方法本身被略过了,取而代之的是方法的结果直接从缓存中找到并返回
    public String getUserByName(String userName)
    {
        String user  = new JSONObject(userService.getUserByName(userName)).toString();
        logger.info("noCache - {}", user);
        return user;
    }

    // http://localhost:8000/service4/getUserByName2?userName=zhangsan
    // http://localhost:8000/service4/getUserByName2?userName=admin
    @RequestMapping("/getUserByName2")
    @Cacheable(value = "userCache", key = "#userName", condition = "#userName.equals('zhangsan')") // userName.equals('zhangsan') 使用缓存
    public String getUserByName2(String userName)
    {
        return getUserByName(userName);
    }


    // http://localhost:8000/service4/getUserByName3?userName=admin
    // http://localhost:8000/service4/getUserByName3?userName=zhangsan
    @RequestMapping("/getUserByName3")
    @Cacheable(value = "userCache", unless = "#result.contains('admin')")  // unless 符合条件的不缓存
    public String getUserById2(@RequestParam(required = true) String userName) {
        String user = new JSONObject(userService.getUserByName(userName)).toString();
        logger.info("noCache - {}", user);
        return user;
    }

    // http://localhost:8000/service4/login?userName=admin&password=111111
    @RequestMapping(value = "/login")
    @CachePut(value = "loginCache", key = "#userName")
    // @CachePut:这个注释可以确保方法被执行,同时方法的返回值也被记录到缓存中
    public String login(HttpServletRequest request, String userName, String password)
    {
        logger.info("noCache - ");
        String msg = "登录失败";
        if (userName!=null && "admin".equals(userName) && "111111".equals(password))
        {
            request.getSession().setAttribute("user", userName);
            request.getSession().setAttribute("message", request.getRequestURL());
            msg = request.getRequestURL()+"登录成功，sessionId为："+request.getSession().getId();
        }
        return msg;
    }


    // http://localhost:8000/service4/deleteUser?userName=admin
    @RequestMapping(value = "/deleteUser")
    @CacheEvict(value = "userCache",beforeInvocation = true) // beforeInvocation = true, allEntries = true
    // allEntries是boolean类型，表示是否需要清除缓存中的所有元素。默认为false，表示不需要，
    // 当指定了allEntries为true时，Spring Cache将忽略指定的key

    // 默认方法如果因为抛出异常而未能成功返回时也不会触发清除操作。
    // 使用beforeInvocation可以改变触发清除操作的时间，当指定该属性值为true时，Spring会在调用该方法之前清除缓存中的指定元素。
    public String deleteUser(@RequestParam(required = true) String userName) {
      //  Boolean aBoolean = userService.deleteUser(userName);
        Boolean aBoolean = false;
        if(!aBoolean) {
            throw new RuntimeException("delete user exception");
        }
        logger.info("noCache - {}", aBoolean);
        return aBoolean.toString();
    }



    // http://localhost:8000/service4/testParam?name=aa
    // testNoParam::aa   默认存入键为 key::参数名
    @RequestMapping(value = "/testParam")
   // @Cacheable(value = "testNoParam")
    // 若key一样，参数也一样，则缓存会被覆盖
    // 此时可使用 自定义keyGenerator
     @Cacheable(value = "loginCache", keyGenerator = "simpleKeyGenerator")
    public String testParam(String name)
    {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello;"+name;
    }

    // http://localhost:8000/service4/testParam2?name=aa
    @RequestMapping(value = "/testParam2")
   // @Cacheable(value = "testNoParam")
     @Cacheable(value = "loginCache", keyGenerator = "simpleKeyGenerator")
    public String testParam2(String name)
    {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return name;
    }

}
