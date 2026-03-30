package com.trung.chat.chatapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Table(name = "conversation_participants")
public class ConversationParticipantId implements Serializable {
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "conversation_id")
    private UUID conversationId;

    public ConversationParticipantId(UUID userId, UUID conversationId) {
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, conversationId);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || getClass()!=(obj.getClass())){
            return false;
        }
        ConversationParticipantId that = (ConversationParticipantId) obj;
        return Objects.equals(userId, that.userId) && Objects.equals(conversationId, that.conversationId);
    }
}
