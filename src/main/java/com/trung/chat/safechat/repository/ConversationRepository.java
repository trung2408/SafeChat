package com.trung.chat.safechat.repository;

import com.trung.chat.safechat.entity.Conversation;
import com.trung.chat.safechat.entity.ConversationReadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation, UUID> {
    @Query("""
        SELECT c FROM Conversation c 
        WHERE c.type = 'PRIVATE' AND 
                EXISTS (
                        SELECT 1 FROM ConversationParticipant p
                        WHERE p.conversation = c AND p.user = :userA
                        )
                AND EXISTS (
                        SELECT 1 FROM ConversationParticipant p 
                        WHERE p.conversation = c AND p.user = :userB
                        )        
        """)
    Conversation findPrivateConversation(UUID userA, UUID userB);
}
