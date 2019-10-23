package com.junzhang.serviceredissession.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;



@Configuration
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400) // 由spring-session-data-redis提供,开启spring session支持
//@EnableRedisHttpSession(redisNamespace = "redis:session")
@EnableRedisHttpSession
public class HttpSessionConfig {

    /**
     * 解决cookie里面的sessionId与session.getId() 不一致问题
     * @return
     */
//    @Bean
//    public DefaultCookieSerializer getDefaultCookieSerializer(){
//        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
//        cookieSerializer.setUseBase64Encoding(false);
//        return cookieSerializer;
//    }



    //session策略，这里配置的是Header方式（有提供Header，Cookie等方式）
    @Bean
    public HttpSessionIdResolver httpSessionStrategy() {
//        return new HeaderHttpSessionIdResolver("X-Auth-Token");
        return HeaderHttpSessionIdResolver.xAuthToken();
    }


}
