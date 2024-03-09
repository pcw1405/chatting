package com.example.chatting.service;

import com.example.chatting.dto.ChatRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    // 채팅방을 생성, 조회하고 하나의 세션에 메시지 발송을 하는 서비스
    // 채팅방 생성 -> Random UUID로 구별ID를 가진 채팅방 객체를 생성하고 채팅방 Map에 추가
     // 메시지 발송 -> 지정한 Websocket 세션에 메시지를 발송

    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms=new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomId){
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(String name){
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom=ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId,chatRoom);
        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session,T message){
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));

        }catch (IOException e){
            log.error(e.getMessage(),e);
            System.out.println("메시지를 보내는데 성공하지못했다 ");
        }
    }

}
