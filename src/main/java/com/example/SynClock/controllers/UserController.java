package com.example.SynClock.controllers;

import com.example.SynClock.model.ApiResponse;
import com.example.SynClock.model.DTOs.CreateUserDTO;
import com.example.SynClock.model.DTOs.UserDTO;
import com.example.SynClock.security.JwtGenerate;
import com.example.SynClock.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServices userServices;
    private final JwtGenerate jwtGenerate;

    public UserController(UserServices userServices, JwtGenerate jwtGenerate) {
        this.userServices = userServices;
        this.jwtGenerate = jwtGenerate;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteAccount(@PathVariable Integer userId) {
        return userServices.deleteAccount(userId.longValue());
    }
}