package com.trung.chat.safechat.websocket.dto;

public class ChatMessage {
    private String content;
    private String conversationId;

    public ChatMessage() {
    }

    public ChatMessage(String content, String conversationId) {
        this.content = content;
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
