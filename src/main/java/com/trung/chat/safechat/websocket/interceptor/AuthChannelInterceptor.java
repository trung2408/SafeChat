package com.trung.chat.safechat.websocket.interceptor;

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

    public AuthChannelInterceptor(JwtService jwtService){
        this.jwtService = jwtService;
    }

    public Message<?>preSend(Message<?> message, MessageChannel channel){
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

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
        return message;
    }
}
