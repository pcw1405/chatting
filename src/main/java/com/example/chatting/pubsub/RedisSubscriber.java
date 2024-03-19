package com.example.chatting.pubsub;

import com.example.chatting.dto.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 Redis Subscriber가 해당 메시지를 받아 처리한다.
     */
    public void sendMessage(String publishMessage) {
        try {
            // ChatMessage 객체로 매핑
            ChatMessage chatMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            // 채팅방을 구독한 클라이언트에게 메시지 발송
            messagingTemplate.convertAndSend("/sub/chat/room/" + chatMessage.getRoomId(), chatMessage);
        } catch (Exception e) {
            // 예외 처리: ObjectMapper가 JSON을 ChatMessage로 매핑하는 도중 예외가 발생한 경우
            log.error("Error parsing JSON message: {}", e.getMessage());
            System.out.println("제이슨파싱에러메시지");
            // 예외 처리를 통해 적절한 대응을 수행하거나, 필요에 따라 예외를 다시 던지거나, 로깅 등을 수행합니다.
            // 예를 들어, 잘못된 형식의 메시지를 받았을 때 로그를 남기거나, 해당 메시지를 무시할 수 있습니다.
        }
    }
}