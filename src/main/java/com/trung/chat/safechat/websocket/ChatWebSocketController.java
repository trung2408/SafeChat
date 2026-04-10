package com.trung.chat.safechat.websocket;

import com.trung.chat.safechat.exception.BussinessException;
import com.trung.chat.safechat.service.ConversationService;
import com.trung.chat.safechat.websocket.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
public class ChatWebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ConversationService conversationService;

    public ChatWebSocketController(SimpMessagingTemplate simpMessagingTemplate, ConversationService conversationService){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.conversationService = conversationService;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage message, Principal principal){
        String senderId = principal.getName();
        String conversationId = message.getConversationId();

        if(!conversationService.isMember(senderId, conversationId)){
            throw new BussinessException("Sender not in conversation");
        }
        simpMessagingTemplate.convertAndSend(
                "/topic/conversations/" + conversationId,
                message
        );

    }
}
