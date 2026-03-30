package com.trung.chat.chatapp.repository;

import com.trung.chat.chatapp.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation, UUID> {

    Conversation findPrivateConversation(UUID userA, UUID userB);
}
