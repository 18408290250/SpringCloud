package com.junzhang.servicerediscachewebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceRedisCacheWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRedisCacheWebfluxApplication.class, args);
    }

}
