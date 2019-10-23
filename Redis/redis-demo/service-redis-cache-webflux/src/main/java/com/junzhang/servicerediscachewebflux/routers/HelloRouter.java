package com.junzhang.servicerediscachewebflux.routers;

import com.junzhang.servicerediscachewebflux.handlerfuncs.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class HelloRouter {

    @Bean
    public RouterFunction<ServerResponse> helloroute(HelloHandler handlerDemo) {
        return RouterFunctions
                .route(RequestPredicates.GET("/hello"), handlerDemo::hello)
                .andRoute(RequestPredicates.GET("/hi"),handlerDemo::hi);
    }
}
