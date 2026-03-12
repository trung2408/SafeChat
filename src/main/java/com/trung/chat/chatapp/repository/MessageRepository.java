package com.trung.chat.chatapp.repository;

import com.trung.chat.chatapp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}
