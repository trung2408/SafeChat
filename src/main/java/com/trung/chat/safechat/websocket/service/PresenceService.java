package com.trung.chat.safechat.websocket.service;

import org.springframework.stereotype.Service;

@Service
public interface PresenceService {
    void setOnline(String userId);

    void setOffline(String userId);

    boolean isOnline(String userId);

    void setActiveConversation(String userId, String conversationId);

    boolean isViewingConversation(String userId, String conversationId);

    void removeUser(String userId);
}
