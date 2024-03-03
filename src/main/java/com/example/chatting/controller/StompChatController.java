package com.example.chatting.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StompChatController {

    private final ChatService chatService;

    @MessageMapping(value = "/chat/enter")
    public void enter(){

    }
}
