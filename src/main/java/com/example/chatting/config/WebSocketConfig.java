package com.example.chatting.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    private final WebSocketChatHandler chatHandler;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }
    // 메시지를 발행하는 요청의 prefix는 /pub로 시작하도록 설정하고 메시지를
    // 구독하는 요청의 prefix는 /sub로 시작하도록 설정합니다

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws-stomp")
                .setAllowedOrigins("http://localhost:8080", "https://yourdomain.com")
                .withSockJS();

    }
    // endpoint를 /ws-stomp로 설정을 한다

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
//        registry.addHandler(chatHandler,"ws/chat").setAllowedOrigins("*");
//    }
//    도메인이 다른 서버에 접속할 수 있도록



}
