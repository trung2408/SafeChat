package com.trung.chat.chatapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "conversation_participants")
public class ConversationParticipant {
    @EmbeddedId
    private ConversationParticipantId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("conversationId")
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    public ConversationParticipant() {
    }

    public ConversationParticipant(User user, Conversation conversation) {
        this.user = user;
        this.conversation = conversation;
        this.id = new ConversationParticipantId(
                user.getUserId(),
                conversation.getConversationId()
        );
    }

    public ConversationParticipantId getId() {
        return id;
    }

    public void setId(ConversationParticipantId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
