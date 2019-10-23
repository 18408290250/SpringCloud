package com.junzhang.serverredissessionwebflux.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.ReactiveMapSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 	该@EnableRedisWebSession注释创建一个Spring bean与名称webSessionManager。
 * 	那个bean实现了WebSessionManager。这是负责替换WebSessionSpring Session支持的实现的原因。在这种情况下，Spring Session由Redis提供支持。
 * 我们创建了一个RedisConnectionFactory将Spring Session连接到Redis Server的程序。我们将连接配置为在默认端口上连接到localhost（6379）有关配置Spring Data Redis的详细信息
 *
 *
 * WebSession与Servlet API及其相比，Spring Session与Spring WebFlux及其集成相当容易HttpSession。Spring WebFlux提供了WebSessionStoreAPI，它提供了持久化的策略WebSession
 */

@EnableRedisWebSession(maxInactiveIntervalInSeconds = 86400*30)
@Configuration
public class WebSessionConfig {

    @Bean
    public WebSessionIdResolver headerWebSessionIdResolver() {
        HeaderWebSessionIdResolver resolver = new HeaderWebSessionIdResolver();
      // resolver.setHeaderName("X-SESSION-ID");  // 默认名，为了方便修改为 X-Auth-Token
        resolver.setHeaderName("X-Auth-Token");
        return resolver;
    }


}
