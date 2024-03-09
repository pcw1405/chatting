package com.example.chatting.dto;

import com.example.chatting.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom {
    private String roomId;
    private String name;
//    private Set<WebSocketSession> sessions =new HashSet<>();
            // 클라이언트 정보를 가지고 있어야 한다
            //WebsocketSession 정보 리스트를 멤버 필드로 갖는다

//    @Builder
    public static ChatRoom create(String name){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId= UUID.randomUUID().toString();
        chatRoom.name=name;
        return chatRoom;
    }
// 웹소켓 관리가 필요없어지므로 세션관리가 필요없어진다 간소화


    // handleActions를 통해 분기처릴르 한다
//    public void handleActions(WebSocketSession session,  ChatMessage chatMessage,ChatService chatService){
//        if(chatMessage.getType().equals(ChatMessage.MessageType.ENTER)){
//            sessions.add(session);
//            chatMessage.setMessage(chatMessage.getSender()+ "님이 입장하셨습니다");
//
//        }
//        sendMessage(chatMessage,chatService);
//    }
//
//    public <T> void sendMessage(T message,ChatService chatService){
//        sessions.parallelStream().forEach(session -> chatService.sendMessage(session,message));
//    }


}
