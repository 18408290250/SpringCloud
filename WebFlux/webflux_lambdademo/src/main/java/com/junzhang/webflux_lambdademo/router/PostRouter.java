package com.junzhang.webflux_lambdademo.router;

import com.junzhang.webflux_lambdademo.handler.PostHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PostRouter {
    @Bean
    public RouterFunction<ServerResponse> postroute(PostHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.POST("/post/findjson")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),handler::find)
                .andRoute(RequestPredicates.POST("/post/findform")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_FORM_URLENCODED)),handler::findform);
    }
}
