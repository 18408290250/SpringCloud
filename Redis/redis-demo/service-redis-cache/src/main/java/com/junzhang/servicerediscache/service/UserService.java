package com.junzhang.servicerediscache.service;

import com.junzhang.servicerediscache.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    public static final List<User> dataList = new ArrayList<>();
    static {
        dataList.add(new User("admin","111111"));
        dataList.add(new User("zhangsan","111111"));
        dataList.add(new User("lisi","111111"));
        dataList.add(new User("张三","111111"));
    }

    public User getUserByName(String username)  {
        System.out.println("执行此方法，说明没有缓存");
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return dataList.stream()
                .filter(x -> x.getUserName().equals(username))
                .findFirst().orElseGet(null);
    }

    public Boolean deleteUser(String userName){
        System.out.println("执行此方法，说明没有缓存");
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user = dataList.stream()
                .filter(x -> x.getUserName().equals(userName))
                .findFirst().orElseGet(null);
        return (null == user)?false:dataList.remove(user);
    }

    public User insertUser(String userName,String password) {
        System.out.println("执行此方法，说明没有缓存");
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dataList.add(new User(userName,password));
        return getUserByName(userName);
    }

}
