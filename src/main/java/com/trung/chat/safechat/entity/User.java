package com.trung.chat.safechat.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String userName;
    private String email;
    private String password;
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    List<Message> messageList = new ArrayList<>();
    // Tương tự như bên Conversation

    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    public void onCreating(){
        this.createdAt = LocalDateTime.now();
    }

    public User() {
    }

    public User(String userName, String email, String password, Role role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public void setUserId(UUID userId) {
        this.id = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getUserId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

//    public List<Message> getMessageList() {
//        return messageList;
//    }
//
//    public void setMessageList(List<Message> messageList) {
//        this.messageList = messageList;
//    }
}
