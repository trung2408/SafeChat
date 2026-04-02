package com.trung.chat.safechat.controller;

import com.trung.chat.safechat.dto.LoginRequest;
import com.trung.chat.safechat.dto.RegisterRequest;
import com.trung.chat.safechat.entity.User;
import com.trung.chat.safechat.exception.NotFountException;
import com.trung.chat.safechat.repository.UserRepository;
import com.trung.chat.safechat.service.AuthService;
import com.trung.chat.safechat.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private AuthService authService;

    public AuthController(JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository, AuthService authService){
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest){
        authService.register(registerRequest.getEmail(), registerRequest.getPassword(), registerRequest.getUsername());
        return "Register succesful";
    }
}
