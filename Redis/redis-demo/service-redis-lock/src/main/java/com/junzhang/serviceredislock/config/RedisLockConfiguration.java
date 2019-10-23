package com.junzhang.serviceredislock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * https://www.cnblogs.com/xuwenjin/p/10681187.html
 * 锁配置
 */

@Configuration
public class RedisLockConfiguration {

    // registryKey和lockKey自动冒号连接，最终key为spring-cloud:lock，值为uuid
    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, "spring-cloud");
    }

}
