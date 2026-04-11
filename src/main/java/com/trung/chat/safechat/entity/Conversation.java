package com.trung.chat.safechat.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private ConversationType type;

//    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<Message> messageList = new ArrayList<>();
    // Trong thực tế, một conversation sẽ có rất nhiều message, nên nếu thiết kế list message nằm toàn bộ trong conversation thì khi query và cần lấy message trong conversation sẽ phải load toàn bộ message, gây tốn tài nguyên và mất rất nhiều thời gian, cách thiết kế đúng là chỉ để message map ManyToOne đến conversation, khi nào cần lấy message thì query trực tiếp bên Message


    // Ở đây thêm field lastMessage với quan hệ Many to one ko phải là vì theo logic thực tế nhiều Conversation có 1 lastMessage mà thực tế một Conversation chỉ có một lastMessage. Nhưng để biểu diễn với JPA rằng lastMessage là FK khóa ngoại của Conversation, trỏ đến/tham chiếu đến Message thì phải dùng Many to one vì Many to one luôn biểu diễn khóa ngoại
    @ManyToOne
    @JoinColumn(name = "last_message_id")
    private Message lastMessage;
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreating(){
        this.createdAt = LocalDateTime.now();
    }

    public Conversation() {
    }

    public Conversation(ConversationType type) {
        this.type = type;
    }

    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public UUID getConversationId() {
        return id;
    }

    public void setConversationId(UUID conversationId) {
        this.id = conversationId;
    }

    public ConversationType getType() {
        return type;
    }

    public void setType(ConversationType type) {
        this.type = type;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
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
