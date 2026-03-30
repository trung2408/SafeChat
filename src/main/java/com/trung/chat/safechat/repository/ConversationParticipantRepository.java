package com.trung.chat.safechat.repository;

import com.trung.chat.safechat.entity.Conversation;
import com.trung.chat.safechat.entity.ConversationParticipant;
import com.trung.chat.safechat.entity.ConversationParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipant, ConversationParticipantId> {
    int countByUser(ConversationParticipant conversationParticipant);

    List<Conversation> findByUserId(UUID userId);

    boolean existsByUserIdAndConversationId(UUID senderId, UUID conversationId);
}
