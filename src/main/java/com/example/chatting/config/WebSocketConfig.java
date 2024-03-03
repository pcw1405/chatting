package com.example.chatting.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // @EnableWebSocketMessageBroker는 stomp를 사용하기 위해 선언하는 어노테이션

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/stomp/chat")
                .setAllowedOrigins("http://localhost:8090")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.setApplicationDestinationPrefixes("/pub");
        //Client에서 SEND 요청을 처리
        registry.enableSimpleBroker("/sub");
        // 해당 경로로 SimpleBroker를 등록. SimpleBroker는 해당하는 경로를 SUBSCRIBE하는
        //Client에게 메세지를 전달하는 간단한 작업을 수행
    }


}
