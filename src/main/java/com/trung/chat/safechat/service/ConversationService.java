package com.trung.chat.chatapp.service;

import com.trung.chat.chatapp.entity.Conversation;
import com.trung.chat.chatapp.entity.ConversationParticipant;
import com.trung.chat.chatapp.entity.ConversationType;
import com.trung.chat.chatapp.entity.User;
import com.trung.chat.chatapp.exception.NotFountException;
import com.trung.chat.chatapp.repository.ConversationParticipantRepository;
import com.trung.chat.chatapp.repository.ConversationRepository;
import com.trung.chat.chatapp.repository.UserRepository;
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

    public Conversation createGroupConversation(List<UUID> userList){
        Conversation conversation = new Conversation(ConversationType.GROUP);
        for(UUID userId : userList){
            User user = userRepository.findById(userId).orElseThrow(() -> new NotFountException("User not found"));
            conversationParticipantRepository.save(new ConversationParticipant(user, conversation));
        }
        return conversation;
    }

    public List<Conversation> getUserConversation(UUID userId){
        return conversationParticipantRepository.findByPaticipantUserId(userId);
    }
}
