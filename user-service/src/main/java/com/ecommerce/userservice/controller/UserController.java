package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.LoginDto;
import com.ecommerce.userservice.dto.UserDto;
import com.ecommerce.userservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody UserDto dto)
    {
        userService.register(dto);
        return "User Registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto dto)
    {
        return userService.login(dto);
    }
}
