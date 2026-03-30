package com.trung.chat.chatapp.controller;

import com.trung.chat.chatapp.dto.message.MessageResponseDTO;
import com.trung.chat.chatapp.dto.message.SendMessageRequestDTO;
import com.trung.chat.chatapp.entity.Message;
import com.trung.chat.chatapp.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> sendMessage(@RequestBody SendMessageRequestDTO requestDTO){
        Message message = messageService.sendMessage(
                requestDTO.getConversationId(),
                requestDTO.getSenderId(),
                requestDTO.getContent()
        );
        MessageResponseDTO dto = new MessageResponseDTO(
                message.getConversation().getConversationId(),
                message.getMessageId(),
                message.getUser().getUserId(),
                message.getContent(),
                message.getCreatedAt()

        );
        return ResponseEntity.ok(dto);
    }
}
