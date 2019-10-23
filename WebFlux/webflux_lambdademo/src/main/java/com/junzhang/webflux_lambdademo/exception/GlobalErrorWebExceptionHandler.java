package com.junzhang.webflux_lambdademo.exception;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * AbstractErrorWebExceptionHandler 抽象类是用来处理全局错误时进行扩展和实现
 * @Order 注解标记 AspectJ 的切面排序，值越小拥有越高的优先级，这里设置优先级偏高。
 * 构造函数将 GlobalErrorAttributes 全局异常属性值类设置到 AbstractErrorWebExceptionHandler 抽象类的局部变量中。
 * 重写 getRoutingFunction 方法，设置对应的 RequestPredicates 和 Mono 服务响应对象
 * 将 GlobalErrorAttributes 的全局异常属性值 map，设置到新的 ServerResponse 即可
 */
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalErrorWebExceptionHandler(GlobalErrorAttributes g, ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(g, new ResourceProperties(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
        super.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(final ServerRequest request) {
        final Map<String, Object> errorPropertiesMap = getErrorAttributes(request, false);
        return ServerResponse.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(errorPropertiesMap));
    }

}
