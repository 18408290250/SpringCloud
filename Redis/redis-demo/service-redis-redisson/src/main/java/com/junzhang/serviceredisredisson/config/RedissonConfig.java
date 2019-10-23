package com.junzhang.serviceredisredisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.IOException;

@Configuration
public class RedissonConfig {

    // 以 config对象形式创建
//    @Bean
//    public Redisson redisson(){
//        Config config = new Config();
//        // 单机模式
//        config.useSingleServer().setAddress("redis://localhost:6379").setPassword("zhangjun").setDatabase(0);
//
//        // 哨兵模式
//        config.useSentinelServers().addSentinelAddress("redis://localhost:6380","redis://localhost:6381","redis://localhost:6382").setPassword("zhangjun")
//                .setMasterName("mymaster")
//                .setPassword("zhangjun").setDatabase(0);
//
//        // 集群模式  无数据库的概念
//        config.useClusterServers().addNodeAddress(
//                "redis://localhost:8001", "redis://localhost:8002", "redis://localhost:8003",
//                "redis://localhost:8004", "redis://localhost:8005", "redis://localhost:8006"
//        ).setPassword("zhangjun").setScanInterval(5000);
//        return (Redisson)Redisson.create(config);
//    }

    // 使用yml创建redisredissonClient
    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {
        RedissonClient redissonClient = Redisson.create(
                Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream()));
        return redissonClient;
    }


}
