package com.example.SynClock.controllers;

import com.example.SynClock.model.ApiResponse;
import com.example.SynClock.model.DTOs.CreateUserDTO;
import com.example.SynClock.model.DTOs.UserDTO;
import com.example.SynClock.model.User;
import com.example.SynClock.security.JwtGenerate;
import com.example.SynClock.services.AuthServices;
import com.example.SynClock.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServices authServices;

    public AuthController(AuthServices authServices) {
        this.authServices = authServices;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody CreateUserDTO loginRequest) {
        return authServices.login(loginRequest);
    }

    @PostMapping("/register")
    public <T> ResponseEntity<ApiResponse<T>> register(@RequestBody CreateUserDTO registerRequest) {
        return authServices.register(registerRequest);
    }

}
