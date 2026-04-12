package com.trung.chat.safechat.websocket.interceptor;

import com.trung.chat.safechat.exception.BussinessException;
import com.trung.chat.safechat.service.ConversationService;
import com.trung.chat.safechat.service.JwtService;
import com.trung.chat.safechat.websocket.dto.StatusDTO;
import com.trung.chat.safechat.websocket.service.PresenceService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;
import java.util.UUID;

public class AuthChannelInterceptor implements ChannelInterceptor {
    private final JwtService jwtService;
    private final ConversationService conversationService;
    private final PresenceService presenceService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public AuthChannelInterceptor(JwtService jwtService, ConversationService conversationService, PresenceService presenceService, SimpMessagingTemplate simpMessagingTemplate){
        this.jwtService = jwtService;
        this.conversationService = conversationService;
        this.presenceService = presenceService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public Message<?>preSend(Message<?> message, MessageChannel channel){
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if(accessor == null){
            return message;
        }
        //xem accessor.getCommand tra ve ket qua gi
        System.out.println(accessor.getCommand());

        if(StompCommand.CONNECT.equals(accessor.getCommand())){
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            if(authHeader != null && authHeader.startsWith("Bearer ")){
                String token = authHeader.substring(7);
                if(jwtService.isValidToken(token)){
                    UUID userId = jwtService.extractUserId(token);
                    UsernamePasswordAuthenticationToken user =
                            new UsernamePasswordAuthenticationToken(
                                    userId.toString(),
                                    null,
                                    new ArrayList<>()
                                    );
                    accessor.setUser(user);

                    presenceService.setOnline(userId.toString());
                    simpMessagingTemplate.convertAndSend(
                            "/topic/status",
                            new StatusDTO(userId.toString(), "ONLINE"));
                }
            }

        }

        if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())){
            String userId = accessor.getUser().getName();
            String destination = accessor.getDestination();

            if(accessor.getUser() == null){
                throw new BussinessException("Unauthorized");
            }
            if(destination != null && destination.startsWith("/topic/conversations/")){
                String[] parts = destination.split("/");
                if(parts.length < 4){
                    throw new BussinessException("Invalid destination");
                }
                String conversationId = parts[3];
                if(!conversationService.isMember(userId, conversationId)){
                    throw new BussinessException("Not allowed");
                }
            }
        }

        if(StompCommand.DISCONNECT.equals(accessor.getCommand())){
            if(accessor.getUser() != null){
                presenceService.setOffline(accessor.getUser().getName());
            }
            simpMessagingTemplate.convertAndSend(
                    "/topic/status",
                    new StatusDTO(accessor.getUser().getName(), "OFFLINE")
            );
        }
        return message;
    }
}
