package com.trung.chat.safechat.security;

import com.trung.chat.safechat.entity.User;
import com.trung.chat.safechat.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUserName(String userId){
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }
}
