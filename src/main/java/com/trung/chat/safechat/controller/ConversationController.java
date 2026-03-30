package com.trung.chat.chatapp.controller;

import com.trung.chat.chatapp.dto.conversation.ConversationResponseDTO;
import com.trung.chat.chatapp.dto.conversation.CreateGroupConversationRequestDTO;
import com.trung.chat.chatapp.dto.conversation.CreatePrivateConversationRequestDTO;
import com.trung.chat.chatapp.dto.message.MessageResponseDTO;
import com.trung.chat.chatapp.entity.Conversation;
import com.trung.chat.chatapp.entity.ConversationType;
import com.trung.chat.chatapp.entity.Message;
import com.trung.chat.chatapp.service.ConversationService;
import com.trung.chat.chatapp.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {
    private final ConversationService conversationService;
    private final MessageService messageService;

    public ConversationController(ConversationService conversationService, MessageService messageService){
        this.conversationService = conversationService;
        this.messageService = messageService;
    }

    @GetMapping("{conversationId}")
    public ResponseEntity<ConversationResponseDTO> getConversation(@PathVariable UUID conversationId){
        Conversation conversation = conversationService.getConversation(conversationId);
        ConversationResponseDTO conversationDTO = new ConversationResponseDTO(
                conversation.getConversationId(),
                conversation.getType(),
                conversation.getCreatedAt()
        );
        return ResponseEntity.ok(conversationDTO);
    }

    @PostMapping("/private")
    public ResponseEntity<ConversationResponseDTO> createPrivateConversation(@RequestBody CreatePrivateConversationRequestDTO requestDTO){
        Conversation conversation = conversationService.createPrivateConversation(
                requestDTO.getUserAId(),
                requestDTO.getUserBId());
        ConversationResponseDTO conversationResponseDTO = new ConversationResponseDTO(
                conversation.getConversationId(),
                ConversationType.PRIVATE,
                conversation.getCreatedAt()
                );
        return ResponseEntity.ok(conversationResponseDTO);
    }

    @PostMapping("/group")
    public ResponseEntity<ConversationResponseDTO> createGroupConversation(@RequestBody CreateGroupConversationRequestDTO requestDTO){
        Conversation conversation = conversationService.createGroupConversation(requestDTO.getMemberList());
        ConversationResponseDTO conversationResponseDTO = new ConversationResponseDTO(
                conversation.getConversationId(),
                ConversationType.GROUP,
                conversation.getCreatedAt()
        );
        return ResponseEntity.ok(conversationResponseDTO);
    }

    @GetMapping("/{id}/messages")
    public Page<MessageResponseDTO> getMessage(@PathVariable UUID id,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "50") int size){
        return messageService.getMessagePage(id, page, size);

    }
}
