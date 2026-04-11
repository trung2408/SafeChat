package com.trung.chat.safechat.dto.conversation;

import com.trung.chat.safechat.entity.ConversationType;
import com.trung.chat.safechat.entity.Message;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConversationResponseDTO {
    private UUID conversationId;
    private ConversationType type;
    private UUID lastMessageId;
    private String lastMessageContent;
    private UUID lastMessageSenderId;
    private LocalDateTime createdAt;

    public ConversationResponseDTO() {
    }

    public ConversationResponseDTO(UUID conversationId, ConversationType type, UUID lastMessageId, String lastMessageContent, UUID lastMessageSenderId, LocalDateTime createdAt) {
        this.conversationId = conversationId;
        this.type = type;
        this.lastMessageId = lastMessageId;
        this.lastMessageContent = lastMessageContent;
        this.lastMessageSenderId = lastMessageSenderId;
        this.createdAt = createdAt;
    }

    public ConversationResponseDTO(UUID conversationId, ConversationType type, LocalDateTime createdAt) {
        this.conversationId = conversationId;
        this.type = type;
        this.createdAt = createdAt;
    }

    public UUID getConversationId() {
        return conversationId;
    }

    public void setConversationId(UUID conversationId) {
        this.conversationId = conversationId;
    }

    public ConversationType getType() {
        return type;
    }

    public void setType(ConversationType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(UUID lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public String getLastMessageContent() {
        return lastMessageContent;
    }

    public void setLastMessageContent(String lastMessageContent) {
        this.lastMessageContent = lastMessageContent;
    }

    public UUID getLastMessageSenderId() {
        return lastMessageSenderId;
    }

    public void setLastMessageSenderId(UUID lastMessageSenderId) {
        this.lastMessageSenderId = lastMessageSenderId;
    }
}
