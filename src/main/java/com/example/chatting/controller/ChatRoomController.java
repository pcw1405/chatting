package com.example.chatting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.chatting.dto.ChatRoom;
import com.example.chatting.dto.LoginInfo;
import com.example.chatting.repo.ChatRoomRepository;
import com.example.chatting.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")

public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/room")
    public String rooms() {
        return "/chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }

    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                String name = auth.getName();
                String token = jwtTokenProvider.generateToken(name);
                return LoginInfo.builder().name(name).token(token).build();
            } else {
                // 인증되지 않은 경우에 대한 처리
                throw new RuntimeException("User is not authenticated");
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            // 예상치 못한 예외 발생 시 로깅하고 다시 던지기
            System.out.println("Error occurred while fetching user information: " + e.getMessage());
            throw new RuntimeException("Internal Server Error");
        }
    }
}
