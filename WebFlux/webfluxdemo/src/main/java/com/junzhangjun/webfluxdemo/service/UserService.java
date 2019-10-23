package com.junzhangjun.webfluxdemo.service;

import com.junzhangjun.webfluxdemo.domain.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    public static  Map<String, User> dataMap = new ConcurrentHashMap<>();
    static {
            dataMap.put("1", new User("aa","m"));
            dataMap.put("2", new User("bb","f"));
            dataMap.put("3", new User("cc","m"));
            dataMap.put("4", new User("dd","m"));
            dataMap.put("5", new User("ee","m"));
            dataMap.put("6", new User("ff","m"));
            dataMap.put("7", new User("gg","m"));
            dataMap.put("8", new User("hh","f"));
            dataMap.put("9", new User("ii","f"));
    }

    public UserService() {
    }

    /**
     * 返回用户列表
     * @return
     */
//    public Collection<User> list(){
//        return UserService.dataMap.values();
//    }

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    public User getById(final String id){
        return UserService.dataMap.get(id);
    }

    public User del(final String id){
        return UserService.dataMap.remove(id);
    }

//    public User save(final User user){
//        UserService.dataMap.put("50",new User(user.getName(),user.getSex()));
//        return UserService.dataMap.get("50");
//    }
}
