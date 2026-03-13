package com.trung.chat.chatapp.service;

import com.trung.chat.chatapp.entity.Conversation;
import com.trung.chat.chatapp.entity.Message;
import com.trung.chat.chatapp.entity.User;
import com.trung.chat.chatapp.exception.BussinessException;
import com.trung.chat.chatapp.exception.NotFountException;
import com.trung.chat.chatapp.repository.ConversationRepository;
import com.trung.chat.chatapp.repository.MessageRepository;
import com.trung.chat.chatapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ConversationService conversationService;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository,  ConversationService conversationService){
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.conversationService = conversationService;
    }

    public Message sendMessage(UUID conversationId, UUID senderId, String content){
        Message message = new Message(content);
        if(!userRepository.existsByUserIdAndConversationId(senderId, conversationId)){
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

    public void markAsRead(){

    }
}
