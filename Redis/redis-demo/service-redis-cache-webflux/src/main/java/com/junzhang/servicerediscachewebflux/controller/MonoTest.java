package com.junzhang.servicerediscachewebflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MonoTest {

    @Autowired
    ReactiveRedisTemplate reactiveRedisTemplate;

    @Autowired  // 类型匹配
    ReactiveStringRedisTemplate reactiveStringRedisTemplate;


    // http://localhost:8010/save?name=zz
    @GetMapping(value = "/save")
    public Mono<String> save(String name) {
        String key = "name_" + name;
        ReactiveValueOperations operations = reactiveRedisTemplate.opsForValue();
        // 设置键值并返回原来的键值
        return operations.getAndSet(key, name);
    }

    // http://localhost:8010/saveStr?name=zz
    @GetMapping(value = "/saveStr")
    public Mono<String> saveStr(String name) {
        String key = "nameStr_" + name;
        ReactiveValueOperations<String, String> operations = reactiveStringRedisTemplate.opsForValue();
        // 设置键值并返回原来的键值
        return operations.getAndSet(key, name);
    }


    // reactiveRedisTemplate 无法获取reactiveStringRedisTemplate 存的内容
    // http://localhost:8010/get1?name=zz
    @GetMapping(value = "/get1")
    public Mono<String> get1(String name) {
        String key = "nameStr_"+name;
        return reactiveRedisTemplate.opsForValue().get(key);
    }

    // reactiveStringRedisTemplate 无法获取 reactiveRedisTemplate 存的内容
    // http://localhost:8010/get?name=zz
    @GetMapping(value = "/get")
    public Mono<String> get(String name) {
        String key = "name_"+name;
        return reactiveStringRedisTemplate.opsForValue().get(key);
    }



}
