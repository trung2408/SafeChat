package com.trung.chat.safechat.websocket.dto;

public class NotificationDTO {
    private String userId;
    private String conversationId;
    private String content;

    public NotificationDTO() {
    }

    public NotificationDTO(String userId, String conversationId, String content) {
        this.userId = userId;
        this.conversationId = conversationId;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
