package com.junzhang.servicerediscache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceRedisCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRedisCacheApplication.class, args);
    }

}
