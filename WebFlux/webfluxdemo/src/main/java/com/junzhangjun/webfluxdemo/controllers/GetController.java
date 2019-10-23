package com.junzhangjun.webfluxdemo.controllers;

import com.junzhangjun.webfluxdemo.domain.User;
import com.junzhangjun.webfluxdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;


@RestController
@RequestMapping("/user")
public class GetController {

    @Autowired
    private  UserService userService;

    @GetMapping("/find")
    public Flux<Object> findById(final String id){
//        Optional<User> first = UserService.dataMap.entrySet().stream()
//                .filter(Objects::nonNull)
////                .peek(x -> System.out.println(x.getKey()))
//                .filter(x -> x.getKey().equals(id))
////                .peek(x -> System.out.println(x.toString()))
//                .map(y -> y.getValue())
//                .findFirst();
//
//
//      return  Mono.fromSupplier(() -> first.orElseGet(() -> new User("无","无")));


        Stream<User> userStream = UserService.dataMap.entrySet().stream()
                .filter(Objects::nonNull)
                .filter(x -> x.getKey().equals(id))
                .map(y -> y.getValue());


        // Mono.justOrEmpty(userStream)  与  Flux.fromStream(userStream) 效果一致
        return Flux.fromStream(userStream); //  ?? []
    }

    @GetMapping("/find1")
    public Mono<User> findById1(final String id){
        return Mono.justOrEmpty(userService.getById(id));
    }

//    @GetMapping("/del")
//    public Mono<User> del(final String id){
//        return Mono.justOrEmpty(userService.del(id));
//    }
//
//    // http://localhost:8080/user/save?name=aa&sex=n
//    // get形式提交，下面两种形式都可以直接获取到请求参数
//    @GetMapping("/save")
//    private Mono<User> save(final User user){
//        System.out.println(user.getName());
//        return Mono.justOrEmpty(userService.save(user));
//    }
//    @GetMapping("/save1")
//    private Mono<User> save(@RequestParam final String name,@RequestParam final String sex){
//        System.out.println(name);
//        return Mono.justOrEmpty(userService.save(new User(name,sex)));
//    }
//
//    // @PathVariable映射URL{}绑定的占位符
////    @GetMapping("/del")
////    public Mono<User> del(@RequestParam String id){
////        return Mono.justOrEmpty(userService.del(id));
////    }
//
//
//    // 若不指明produces为stream，仍是以前的效果
//    @GetMapping(value = "/list",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
////   @GetMapping(value = "/list")
//    public Flux<User> list(){
//        return Flux.fromIterable(userService.list()).delayElements(Duration.ofSeconds(1));
//    }

}
