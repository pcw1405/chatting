package com.example.chatting.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ChatRoom {
    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions =new HashSet<>();
            // 클라이언트 정보를 가지고 있어야 한다
            //WebsocketSession 정보 리스트를 멤버 필드로 갖는다

    @Builder
    public ChatRoom(String roomId,String name){
        this.roomId=roomId;
        this.name=name;
    }

    // handleActions를 통해 분기처릴르 한다
    public void handleActions(WebSocketSession session,ChatService chatService, ChatMessage chatMessage){
        if(chatMessage.getType().equals(ChatMessage.MessageType.ENTER)){
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender()+ "님이 입장하셨습니다");
            sendMessage(chatMessage, chatService);
        }
    }

    public <T> void sendMessage(T message,ChatService chatService){
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session,message));
    }


}
