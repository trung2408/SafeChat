package com.trung.chat.safechat.repository;

import com.trung.chat.safechat.entity.ConversationRead;
import com.trung.chat.safechat.entity.ConversationReadId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConversationReadRepository extends JpaRepository<ConversationRead, ConversationReadId> {

    ConversationRead findByConversationIdAndUserId(UUID conversationId, UUID userId);
}
