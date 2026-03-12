package com.trung.chat.chatapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID conversationId;
    @Enumerated(EnumType.STRING)
    private ConversationType type;
    @OneToMany(mappedBy = "messageId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Message> messageList = new ArrayList<>();
    private LocalDateTime createdAt;

    public Conversation() {
    }

    public Conversation(ConversationType type) {
        this.type = type;
    }

    public void onCreate(){
        this.createdAt = LocalDateTime.now();
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

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
