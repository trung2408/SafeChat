package com.trung.chat.safechat.repository;

import com.trung.chat.safechat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUserIdAndConversationId(UUID senderId, UUID conversationId);
}
