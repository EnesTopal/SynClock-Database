package com.example.SynClock.controllers;

import com.example.SynClock.model.DTOs.CreateUserDTO;
import com.example.SynClock.model.DTOs.UserDTO;
import com.example.SynClock.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createAccount(@RequestBody CreateUserDTO userRequest) {
        return userServices.createAccount(userRequest);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long userId) {
        return userServices.deleteAccount(userId);
    }
}