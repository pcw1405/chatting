package com.example.chatting.config.handler;

import com.example.chatting.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// jwt토큰의 유효성을 검사한다
@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    // websocket을 통해 들어온 요청이 처리 되기전 실행된다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        // websocket 연결시 헤더의 jwt token 검증
        if (StompCommand.CONNECT == accessor.getCommand()) {
            try {
                jwtTokenProvider.validateToken(accessor.getFirstNativeHeader("token"));
            } catch (Exception e) {
                log.error("JWT 토큰 유효성 검증 중 오류 발생: {}", e.getMessage());
                System.out.println("토큰 유효성 검증 중 오류 발생");
                // 예외 처리를 추가하거나 원하는 대응을 수행할 수 있습니다.
                // 예를 들어, 유효하지 않은 토큰에 대한 응답을 전송할 수 있습니다.
                // 예를 들어, 연결을 거부하거나, 연결이 실패했다는 메시지를 보낼 수 있습니다.
                // 원하는 처리 방법에 따라 구현하세요.
            }
        }
        return message;
    }
}
