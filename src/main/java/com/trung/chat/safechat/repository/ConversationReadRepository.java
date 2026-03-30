package com.trung.chat.chatapp.repository;

import com.trung.chat.chatapp.entity.ConversationRead;
import com.trung.chat.chatapp.entity.ConversationReadId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConversationReadRepository extends JpaRepository<ConversationRead, ConversationReadId> {

    ConversationRead findByConversationIdAndUserId(UUID conversationId, UUID userId);
}
