package com.junzhang.serviceredisredisson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceRedisRedissonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRedisRedissonApplication.class, args);
    }

}
