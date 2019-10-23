package com.junzhang.servicezpubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceZpubsubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceZpubsubApplication.class, args);
    }

}
