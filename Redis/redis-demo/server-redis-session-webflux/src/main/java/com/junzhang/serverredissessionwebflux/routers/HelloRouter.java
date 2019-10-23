package com.junzhang.serverredissessionwebflux.routers;

import com.junzhang.serverredissessionwebflux.handlerfuncs.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class HelloRouter {

//    @Bean
//    public RouterFunction<ServerResponse> helloroute(HelloHandler handlerDemo) {
//        return RouterFunctions
//                .route(RequestPredicates.GET("/login"), handlerDemo::login)
//                .andRoute(RequestPredicates.GET("/index"), handlerDemo::index);
//    }
}
