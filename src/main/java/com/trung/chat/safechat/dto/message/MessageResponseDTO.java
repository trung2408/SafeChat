package com.trung.chat.safechat.dto.message;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageResponseDTO {
    private UUID conversationId;
    private UUID messageId;
    private UUID senderId;
    private String content;
    private LocalDateTime createdAt;

    public MessageResponseDTO() {
    }

    public MessageResponseDTO(UUID conversationId, UUID messageId, UUID senderId, String content, LocalDateTime createdAt) {
        this.conversationId = conversationId;
        this.messageId = messageId;
        this.senderId = senderId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public UUID getConversationId() {
        return conversationId;
    }

    public void setConversationId(UUID conversationId) {
        this.conversationId = conversationId;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
