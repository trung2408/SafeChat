package com.trung.chat.safechat.dto.message;

import java.util.UUID;

public class SendMessageRequestDTO {
    private UUID conversationId;
    private UUID senderId;
    private String content;

    public SendMessageRequestDTO() {
    }

    public SendMessageRequestDTO(UUID conversationId, UUID senderId, String content) {
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.content = content;
    }

    public UUID getConversationId() {
        return conversationId;
    }

    public void setConversationId(UUID conversationId) {
        this.conversationId = conversationId;
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
}
