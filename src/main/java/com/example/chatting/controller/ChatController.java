package com.example.chatting.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

@Controller
@Log4j2
public class ChatController {


    public String chatGET(){
        log.info("@ChatController.chat GET()");
        return "chater";
    }
}
