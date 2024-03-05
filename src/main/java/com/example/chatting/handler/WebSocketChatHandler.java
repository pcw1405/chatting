package com.example.chatting.handler;

import com.example.chatting.dto.ChatMessage;
import com.example.chatting.dto.ChatRoom;
import com.example.chatting.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
@Log4j2
public class WebSocketChatHandler extends TextWebSocketHandler {
    // 여러 클라이언트가 받아 처리해줄 handler

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        String payload = message.getPayload();
        log.info("payload {}" + payload);
        //페이로드란 전송되는 데이터를 의미한다
//        TextMessage textMessage = new TextMessage("Welcome to chat server");
//        session.sendMessage(textMessage);
        ChatMessage chatMessage =objectMapper.readValue(payload,ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handleActions(session,  chatMessage ,chatService);

    }
//
//    // Client가 접속 시 호출 되는 메서드
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
//        list.add(session);
//        log.info(session + "클라이언트 접속");
//    }
//
//    // Client가 접속 해제 시 호출되는 메서드
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception{
//        log.info(session + "클라이언트 접속해제");
//        list.remove(session);
//    }
}
