package com.trung.chat.safechat.websocket;

import com.trung.chat.safechat.exception.BussinessException;
import com.trung.chat.safechat.exception.NotFountException;
import com.trung.chat.safechat.repository.ConversationRepository;
import com.trung.chat.safechat.repository.UserRepository;
import com.trung.chat.safechat.service.ConversationService;
import com.trung.chat.safechat.websocket.dto.ChatMessage;
import com.trung.chat.safechat.websocket.dto.NotificationDTO;
import com.trung.chat.safechat.websocket.service.PresenceService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class ChatWebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ConversationService conversationService;
    private final PresenceService presenceService;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public ChatWebSocketController(SimpMessagingTemplate simpMessagingTemplate, ConversationService conversationService, PresenceService presenceService, ConversationRepository conversationRepository, UserRepository userRepository){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.conversationService = conversationService;
        this.presenceService = presenceService;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
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

        String senderName = userRepository.findById(UUID.fromString(senderId)).orElseThrow(()-> new NotFountException("Sender not found")).getUserName();
        List<String> members = conversationService.getMembers(conversationId);
        for(String userId : members){
            if(userId.equals(senderId))
                continue;
            if(!presenceService.isViewingConversation(userId, conversationId) && presenceService.isOnline(userId)){
                simpMessagingTemplate.convertAndSendToUser(
                        userId,
                        "/queue/notifications",
                        new NotificationDTO(
                                userId,
                                message.getConversationId(),
                                message.getContent()
                        )
                );
            }
        }
    }

    @MessageMapping("/chat.typing")
    public void typing(ChatMessage message, Principal principal){
        String userId = principal.getName();
        simpMessagingTemplate.convertAndSend("/topic/conversations/"+message.getConversationId()+"/typing", userId);
    }

    @MessageMapping("/chat.active")
    public void active(ChatMessage message, Principal principal){
        String userId = principal.getName();
        String conversationId = message.getConversationId();
        presenceService.setActiveConversation(userId, conversationId);
    }
}
