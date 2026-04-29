package com.ecommerce.userservice.service;

import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.dto.LoginDto;
import com.ecommerce.userservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void register(UserDto dto)
    {
        if(userRepository.findByEmail(dto.getEmail()).isPresent())
        {
            throw new RuntimeException("Email already registered. Try another.");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public String login(LoginDto dto)
    {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!encoder.matches(dto.getPassword(), user.getPassword()))
        {
            throw new RuntimeException("Invalid Password");
        }

        return "Login successful for user: " + user.getEmail();
    }
}
