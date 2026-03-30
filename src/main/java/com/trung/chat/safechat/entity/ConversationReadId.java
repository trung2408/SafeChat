package com.trung.chat.safechat.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ConversationReadId implements Serializable {
    private UUID conversationId;
    private UUID userId;
}
