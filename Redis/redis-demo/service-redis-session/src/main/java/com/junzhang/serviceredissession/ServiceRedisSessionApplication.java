package com.junzhang.serviceredissession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceRedisSessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRedisSessionApplication.class, args);
    }

}
