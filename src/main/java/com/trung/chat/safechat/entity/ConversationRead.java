package com.trung.chat.safechat.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversation_reads")
public class ConversationRead {
    @EmbeddedId
    private ConversationParticipantId id;

    @ManyToOne
    @MapsId("conversationId")
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    private Message lastReadMessage;

    private LocalDateTime readAt;

    public ConversationRead() {
    }

    public ConversationRead(Conversation conversation, User user) {
        this.id = new ConversationParticipantId(user.getUserId(), conversation.getConversationId());
        this.conversation = conversation;
        this.user = user;
    }

    public ConversationParticipantId getId() {
        return id;
    }

    public void setId(ConversationParticipantId id) {
        this.id = id;
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

    public Message getLastReadMessage() {
        return lastReadMessage;
    }

    public void setLastReadMessage(Message lastReadMessage) {
        this.lastReadMessage = lastReadMessage;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }
}
