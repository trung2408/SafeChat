package com.trung.chat.safechat.dto.conversation;

import java.util.UUID;

public class CreatePrivateConversationRequestDTO {
    private UUID userAId;
    private UUID userBId;

    public CreatePrivateConversationRequestDTO() {
    }

    public CreatePrivateConversationRequestDTO(UUID userAId, UUID userBId) {
        this.userAId = userAId;
        this.userBId = userBId;
    }

    public UUID getUserAId() {
        return userAId;
    }

    public void setUserAId(UUID userAId) {
        this.userAId = userAId;
    }

    public UUID getUserBId() {
        return userBId;
    }

    public void setUserBId(UUID userBId) {
        this.userBId = userBId;
    }
}
