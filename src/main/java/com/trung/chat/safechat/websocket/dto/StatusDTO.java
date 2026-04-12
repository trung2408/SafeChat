package com.trung.chat.safechat.websocket.dto;

public class StatusDTO {
    private String userId;
    private String status;

    public StatusDTO() {
    }

    public StatusDTO(String userId, String status) {
        this.userId = userId;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
