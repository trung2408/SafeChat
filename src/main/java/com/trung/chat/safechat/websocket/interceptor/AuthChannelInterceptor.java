package com.trung.chat.safechat.websocket.interceptor;

import com.trung.chat.safechat.exception.BussinessException;
import com.trung.chat.safechat.service.ConversationService;
import com.trung.chat.safechat.service.JwtService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
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

    public AuthChannelInterceptor(JwtService jwtService, ConversationService conversationService){
        this.jwtService = jwtService;
        this.conversationService = conversationService;
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
                }
            }

        }

        if(StompCommand.SUBSCRIBE.equals(accessor.getCommand())){
            String userId = accessor.getUser().getName();
            String destination = accessor.getDestination();

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
        return message;
    }
}
