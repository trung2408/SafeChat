package com.trung.chat.safechat.repository;

import com.trung.chat.safechat.entity.Conversation;
import com.trung.chat.safechat.entity.ConversationParticipant;
import com.trung.chat.safechat.entity.ConversationParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipant, ConversationParticipantId> {
    int countByUser(ConversationParticipant conversationParticipant);

    @Query("""
        SELECT cp.conversation FROM ConversationParticipant cp
        LEFT JOIN cp.conversation.lastMessage lm 
        WHERE cp.user.id = :userId
        ORDER BY lm.createdAt DESC 
    """)
    List<Conversation> findUserConversations(UUID userId);

    boolean existsByUserIdAndConversationId(UUID senderId, UUID conversationId);
}
