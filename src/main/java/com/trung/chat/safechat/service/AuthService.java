package com.trung.chat.safechat.service;

import com.trung.chat.safechat.entity.Role;
import com.trung.chat.safechat.entity.User;
import com.trung.chat.safechat.exception.BussinessException;
import com.trung.chat.safechat.exception.NotFountException;
import com.trung.chat.safechat.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String email, String password){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new NotFountException("Email not found"));
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BussinessException("Password incorrect");
        }
        return jwtService.generateToken(user);
    }

    public void register(String email, String password, String username){
        if(userRepository.existsByEmail(email)){
            throw new BussinessException("Email already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setUserName(username);
        user.setRole(Role.USER);
        userRepository.save(user);
    }
}
