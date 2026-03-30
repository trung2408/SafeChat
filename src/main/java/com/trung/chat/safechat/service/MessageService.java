package com.trung.chat.safechat.service;

import com.trung.chat.safechat.dto.message.MessageResponseDTO;
import com.trung.chat.safechat.entity.*;
import com.trung.chat.safechat.exception.BussinessException;
import com.trung.chat.safechat.exception.NotFountException;
import com.trung.chat.safechat.repository.ConversationParticipantRepository;
import com.trung.chat.safechat.repository.ConversationReadRepository;
import com.trung.chat.safechat.repository.MessageRepository;
import com.trung.chat.safechat.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ConversationService conversationService;
    private final ConversationReadRepository conversationReadRepository;
    private final ConversationParticipantRepository conversationParticipantRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository, ConversationService conversationService, ConversationReadRepository conversationReadRepository, ConversationParticipantRepository conversationParticipantRepository){
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.conversationService = conversationService;
        this.conversationReadRepository = conversationReadRepository;
        this.conversationParticipantRepository = conversationParticipantRepository;
    }

    public Message sendMessage(UUID conversationId, UUID senderId, String content){
        Message message = new Message(content);
        if(!conversationParticipantRepository. existsByUserIdAndConversationId(senderId, conversationId)){
            throw new BussinessException("Sender is not in this conversation");
        }

        User sender = userRepository.findById(senderId).orElseThrow(() -> new NotFountException("User not found"));
        message.setUser(sender);
        Conversation conversation = conversationService.getConversation(conversationId);
        message.setConversation(conversation);
        message.setCreatedAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public Message getMessage( UUID messageId){
        return messageRepository.findById(messageId).orElseThrow(() -> new NotFountException("Message not found"));
    }

    public void markAsRead(UUID userId, UUID conversationId){
        Message lastMessage = messageRepository.findTopByConversationOrderByCreatedAtDesc(conversationId).orElse(null);
        ConversationRead read = conversationReadRepository.findByConversationIdAndUserId(conversationId, userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFountException("User not found"));
        Conversation conversation = conversationService.getConversation(conversationId);
        if(read == null){
            read = new ConversationRead(conversation, user);
        }
        else{
            read.setUser(user);
            read.setConversation(conversation);
        }
        read.setLastReadMessage(lastMessage);
        read.setReadAt(LocalDateTime.now());
        conversationReadRepository.save(read);
    }

    public Page<MessageResponseDTO> getMessagePage(UUID conversationId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Message> messagePage = messageRepository.findByConversationIdOrderByCreatedAtDesc(conversationId, pageable);
        return messagePage.map(message -> new MessageResponseDTO(
                message.getConversation().getConversationId(),
                message.getMessageId(),
                message.getUser().getUserId(),
                message.getContent(),
                message.getCreatedAt()
        ));
    }
}
