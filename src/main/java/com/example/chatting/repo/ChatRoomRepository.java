package com.example.chatting.repo;

import com.example.chatting.dto.ChatRoom;
import com.example.chatting.pubsub.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {

    //채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    //구독 처리 서비스
    private static final String CHAT_ROOMS ="CHAT_ROOM";


    // 채팅방의 대화 메시지를 발행하기 위한


    @PostConstruct
    private void init(){

        opsHashChatRoom=redisTemplate.opsForHash();
    }

    public List<ChatRoom> findAllRoom(){
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    public ChatRoom findRoomById(String id){

        return opsHashChatRoom.get(CHAT_ROOMS,id);
    }

//    서버간 채팅방 공유를 위해 redis hash에 저장한다
    public ChatRoom createChatRoom(String name){
        ChatRoom chatRoom = ChatRoom.create(name);
        opsHashChatRoom.put(CHAT_ROOMS,chatRoom.getRoomId(),chatRoom);
        return chatRoom;
    }

    // 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다


}
