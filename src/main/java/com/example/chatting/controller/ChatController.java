package com.example.chatting.controller;

import com.example.chatting.dto.ChatMessage;
import com.example.chatting.repo.ChatRoomRepository;
import com.example.chatting.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {


    private final ChatRoomRepository chatRoomRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @MessageMapping("chat/message")
    public void message(ChatMessage message, @Header("token") String token){
        String nickname = jwtTokenProvider.getUserNameFromJwt(token);

        if(ChatMessage.MessageType.ENTER.equals(message.getType())) {
//            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.getSender("[알림]");
            message.setMessage(nickname+ "님이 입장하셨습니다.");
        }
//        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()),message);
    }
    //리스너 연동
}
