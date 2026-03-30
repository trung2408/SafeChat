package com.trung.chat.safechat.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User user;
    private String content;
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreating(){
        this.createdAt = LocalDateTime.now();
    }

    public Message() {
    }

    public Message(String content) {
        this.content = content;
    }

    public UUID getMessageId() {
        return id;
    }

    public void setMessageId(UUID messageId) {
        this.id = messageId;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
