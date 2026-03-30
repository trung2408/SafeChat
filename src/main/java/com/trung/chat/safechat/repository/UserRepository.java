package com.trung.chat.chatapp.repository;

import com.trung.chat.chatapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUserIdAndConversationId(UUID senderId, UUID conversationId);
}
