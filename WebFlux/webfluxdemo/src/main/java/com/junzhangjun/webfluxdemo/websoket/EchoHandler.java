package com.junzhangjun.webfluxdemo.websoket;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * 创建 WebSocket 的处理器 EchoHandler
 * WebSocketHandler 接口，实现该接口来处理 WebSokcet 消息。
 *
 * 在线测试工具：http://coolaf.com/tool/chattest
 *
 */
@Component
public class EchoHandler implements WebSocketHandler {
    // 接口 WebSocketHandler 的方法 handle 的参数是接口 WebSocketSession 的对象，可以用来获取客户端信息、接送消息和发送消息
    // 向客户端发送数据流，当数据流结束时，往客户端的写操作也会随之结束，此时返回的 Mono<Void> 会发出一个完成信号
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        // 在 handle 方法，使用 map 操作对 receive 方法得到的 Flux<WebSocketMessage>中包含的消息继续处理，然后直接由 send 方法来发送


        // send 方法的参数是一个 Publisher<WebSocketMessage>对象，表示要发送的消息流。
        // WebSocketSession 的 receive 方法的返回值是一个 Flux<WebSocketMessage>对象，表示的是接收到的消息流。
        //在receive() 方法中接收消息，使用 map 操作获取的 Flux 中包含的消息持续处理，并拼接出返回消息 Flux 对象。
        return session.send(
                session.receive()
                        .map(msg ->
                            session.textMessage("服务端返回： -> " + msg.getPayloadAsText())
                        ));

    }
}
