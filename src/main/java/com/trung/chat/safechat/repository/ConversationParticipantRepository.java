package com.trung.chat.chatapp.repository;

import com.trung.chat.chatapp.entity.Conversation;
import com.trung.chat.chatapp.entity.ConversationParticipant;
import com.trung.chat.chatapp.entity.ConversationParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipant, ConversationParticipantId> {
    int countByUser(ConversationParticipant conversationParticipant);

    List<Conversation> findByPaticipantUserId(UUID userId);
}
