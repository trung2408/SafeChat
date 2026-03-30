package com.trung.chat.chatapp.dto.conversation;

import java.util.List;
import java.util.UUID;

public class CreateGroupConversationRequestDTO {
    private String name;
    private List<UUID> memberList;

    public CreateGroupConversationRequestDTO() {
    }

    public CreateGroupConversationRequestDTO(String name, List<UUID> memberList) {
        this.name = name;
        this.memberList = memberList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UUID> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<UUID> memberList) {
        this.memberList = memberList;
    }
}
