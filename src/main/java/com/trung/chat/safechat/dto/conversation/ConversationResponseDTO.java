package com.trung.chat.safechat.dto.conversation;

import com.trung.chat.safechat.entity.ConversationType;

import java.time.LocalDateTime;
import java.util.UUID;

public class ConversationResponseDTO {
    private UUID conversationId;
    private ConversationType type;
    private LocalDateTime createdAt;

    public ConversationResponseDTO() {
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
}
