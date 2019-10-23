package com.junzhang.webflux_lambdademo.router;

import com.junzhang.webflux_lambdademo.handler.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * 注册 RouterFunction
 *
 * 通过函数式接口 org.springframework.web.reactive.function.server.RouterFunction 来完成路由配置
 * 接口 RouterFunction 的方法 Mono<HandlerFunction<T extends ServerResponse>> route(ServerRequest request)对每个 ServerRequest，
 * 都返回对应的 0 个或 1 个 HandlerFunction 对象，以 Mono<HandlerFunction>来表示。
 *
 * 当找到对应的 HandlerFunction 时，该 HandlerFunction 被调用来处理该 ServerRequest，并把得到的 ServerResponse 返回。
 * 在使用 WebFlux 的 Spring Boot 应用中，只需要创建 RouterFunction 类型的 bean，就会被自动注册来处理请求并调用相应的HandlerFunction
 */

@Configuration
public class HelloRouter {
    //这里将一个 GET 请求 /hello 路由到处理器 HelloHandler的 hello方法上
//    RouterFunctions.route(RequestPredicate,HandlerFunction) 方法，
    // 对应的入参是请求参数和处理函数，如果请求匹配，就调用对应的处理器函数

    @Bean
    public RouterFunction<ServerResponse> helloroute(HelloHandler handlerDemo) {

        // 以前的写法
        // 映射
//        RequestPredicate requestPredicate = RequestPredicates.GET("/hello");
//        // 处理请求
//        HandlerFunction handlerFunction = new HandlerFunction() {
//            @Override
//            public Mono handle(ServerRequest serverRequest) {
//                return ServerResponse.ok().body(Mono.justOrEmpty("hello"),String.class);
////                return Mono.justOrEmpty("hello");
//            }
//        };
//        return RouterFunctions.route(requestPredicate,handlerFunction);



//        HandlerFunction

        //  route第二个参数是一个实现了HandlerFunction的handle方法的实例对象，这里用lambda表达式代替
        return RouterFunctions
                .route(RequestPredicates.GET("/hello"), handlerDemo::hello)
                .and(RouterFunctions.route((RequestPredicates.GET("/helloCity")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN))),handlerDemo::helloCity));


//        RouterFunction<ServerResponse> otherRoute = null;
//       return RouterFunctions.route()
//                .GET("/person/{id}",RequestPredicates.accept(APPLICATION_JSON), handlerDemo::hello)
//                .POST("/person", RequestPredicates.accept(APPLICATION_JSON), handlerDemo::helloCity)
//                .add(otherRoute)
//                .build();

    }
}
