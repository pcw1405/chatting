package com.example.chatting.controller;

import com.example.chatting.dto.ChatMessage;
import com.example.chatting.repo.ChatRoomRepository;
import com.example.chatting.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;


import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {


    private final RedisTemplate<String, Object> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChannelTopic channelTopic;

    @MessageMapping("chat/message")
    public void message(ChatMessage message, @Header("token") String token){
        String nickname = jwtTokenProvider.getUserNameFromJwt(token);
        //로그인 회원 정보로 대화명 설정
        if(ChatMessage.MessageType.ENTER.equals(message.getType())) {
//            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setSender("[알림]");
            message.setMessage(nickname+ "님이 입장하셨습니다.");
        }
//        websocket에 발행된 메시지를 redis로 발행
        redisTemplate.convertAndSend(channelTopic.getTopic(),message);
    }
    //리스너 연동
}
