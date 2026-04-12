package com.trung.chat.safechat.websocket.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PresenceServiceImpl implements PresenceService{
    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();
    private final Map<String, String> activeConversation = new ConcurrentHashMap<>();

    @Override
    public void setOnline(String userId) {
        onlineUsers.add(userId);
    }

    @Override
    public void setOffline(String userId) {
        onlineUsers.remove(userId);
        activeConversation.remove(userId);
    }

    @Override
    public boolean isOnline(String userId) {
        return onlineUsers.contains(userId);
    }

    @Override
    public void setActiveConversation(String userId, String conversationId) {
        activeConversation.put(userId, conversationId);
    }

    @Override
    public boolean isViewingConversation(String userId, String conversationId) {
        return conversationId.equals(activeConversation.get(userId));
    }

    @Override
    public void removeUser(String userId) {
        onlineUsers.remove(userId);
        activeConversation.remove(userId);
    }
}
