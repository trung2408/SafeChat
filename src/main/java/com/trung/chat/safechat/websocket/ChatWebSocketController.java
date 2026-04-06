package com.trung.chat.safechat.websocket;

import com.trung.chat.safechat.websocket.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatWebSocketController(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage message){
        System.out.println("Recieved:" + message.getContent());
        simpMessagingTemplate.convertAndSend("/topic/conversations/" + message.getConversationId(), message);
    }
}
