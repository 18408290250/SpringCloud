package com.junzhang.serviceredislock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;


@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400) // 由spring-session-data-redis提供,开启spring session支持
public class HttpSessionConfig {

    //session策略，这里配置的是Header方式（有提供Header，Cookie等方式）
    @Bean
    public HttpSessionIdResolver httpSessionStrategy() {
//        return new HeaderHttpSessionIdResolver("X-Auth-Token");
        return HeaderHttpSessionIdResolver.xAuthToken();
    }


}
