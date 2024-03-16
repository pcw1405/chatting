package com.example.chatting.controller;

import com.example.chatting.dto.ChatMessage;
import com.example.chatting.dto.ChatRoom;
import com.example.chatting.pubsub.RedisPublisher;
import com.example.chatting.repo.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;

    @MessageMapping("chat/message")
    public void message(ChatMessage message){
        if(ChatMessage.MessageType.ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()),message);
    }
    //리스너 연동
}
