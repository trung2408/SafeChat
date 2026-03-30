package com.trung.chat.safechat.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID conversationId;
    @Enumerated(EnumType.STRING)
    private ConversationType type;

//    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<Message> messageList = new ArrayList<>();
    // Trong thực tế, một conversation sẽ có rất nhiều message, nên nếu thiết kế list message nằm toàn bộ trong conversation thì khi query và cần lấy message trong conversation sẽ phải load toàn bộ message, gây tốn tài nguyên và mất rất nhiều thời gian, cách thiết kế đúng là chỉ để message map ManyToOne đến conversation, khi nào cần lấy message thì query trực tiếp bên Message

    private LocalDateTime createdAt;

    public Conversation() {
    }

    public Conversation(ConversationType type) {
        this.type = type;
    }

    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public UUID getConversationId() {
        return conversationId;
    }

    public void setConversationId(UUID conversationId) {
        this.conversationId = conversationId;
    }

    public ConversationType getType() {
        return type;
    }

    public void setType(ConversationType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

//    public List<Message> getMessageList() {
//        return messageList;
//    }
//
//    public void setMessageList(List<Message> messageList) {
//        this.messageList = messageList;
//    }
}
