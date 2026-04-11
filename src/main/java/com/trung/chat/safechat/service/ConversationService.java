package com.trung.chat.safechat.service;

import com.trung.chat.safechat.dto.conversation.ConversationResponseDTO;
import com.trung.chat.safechat.entity.*;
import com.trung.chat.safechat.exception.NotFountException;
import com.trung.chat.safechat.repository.ConversationParticipantRepository;
import com.trung.chat.safechat.repository.ConversationRepository;
import com.trung.chat.safechat.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final ConversationParticipantRepository conversationParticipantRepository;
    private final UserRepository userRepository;

    public ConversationService(ConversationRepository conversationRepository, ConversationParticipantRepository conversationParticipantRepository, UserRepository userRepository){
        this.conversationRepository = conversationRepository;
        this.conversationParticipantRepository = conversationParticipantRepository;
        this.userRepository = userRepository;
    }

    public Conversation getConversation(UUID conversationId){
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new NotFountException("Conversation not found"));
        return conversation;
    }
    @Transactional
    public Conversation createPrivateConversation(UUID userA, UUID userB){
        Conversation existing = conversationRepository.findPrivateConversation(userA, userB);
        if(existing != null){
            return existing;
        }
        else {
            Conversation conversation = new Conversation(ConversationType.PRIVATE);
            conversationRepository.save(conversation);
            User UserA = userRepository.findById(userA).orElseThrow(()-> new NotFountException("User not found"));
            User UserB = userRepository.findById(userB).orElseThrow(()-> new NotFountException("User not found"));

            conversationParticipantRepository.save(new ConversationParticipant(UserA, conversation));
            conversationParticipantRepository.save(new ConversationParticipant(UserB, conversation));
            return conversation;
        }
    }
    @Transactional
    public Conversation createGroupConversation(List<UUID> userList){
        Conversation conversation = new Conversation(ConversationType.GROUP);
        conversationRepository.save(conversation);
        for(UUID userId : userList){
            User user = userRepository.findById(userId).orElseThrow(() -> new NotFountException("User not found"));
            conversationParticipantRepository.save(new ConversationParticipant(user, conversation));
        }
        return conversation;
    }

    public List<ConversationResponseDTO> getUserConversation(UUID userId){
        List<Conversation> conversations = conversationParticipantRepository.findUserConversations(userId);

        return conversations.stream().map(c -> {
            Message lastMessage = c.getLastMessage();

            return new ConversationResponseDTO(
                    c.getConversationId(),
                    c.getType(),
                    lastMessage.getMessageId(),
                    lastMessage.getContent(),
                    lastMessage.getUser().getUserId(),
                    lastMessage.getCreatedAt());
        }).toList();
    }

    public boolean isMember(String userId, String conversationId){
        return conversationParticipantRepository.existsByUserIdAndConversationId(UUID.fromString(userId), UUID.fromString(conversationId));
    }
}
