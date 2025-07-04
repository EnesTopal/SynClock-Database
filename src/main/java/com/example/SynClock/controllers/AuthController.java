package com.example.SynClock.controllers;

import com.example.SynClock.model.ApiResponse;
import com.example.SynClock.model.DTOs.CreateUserDTO;
import com.example.SynClock.model.DTOs.UserDTO;
import com.example.SynClock.model.User;
import com.example.SynClock.security.JwtGenerate;
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
    private final AuthenticationManager authenticationManager;
    private final JwtGenerate jwtGenerate;
    private final UserServices userServices;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtGenerate jwtGenerate,
                          UserServices userServices,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerate = jwtGenerate;
        this.userServices = userServices;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody CreateUserDTO loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getUserpassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtGenerate.generateJwtToken(authentication);

        ApiResponse<String> response = new ApiResponse<>("Login successful", jwtToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public <T> ResponseEntity<ApiResponse<T>> register(@RequestBody CreateUserDTO registerRequest) {
        if (userServices.getOneUserByUserName(registerRequest.getUsername()) != null) {
            ApiResponse<T> response = new ApiResponse<>("Username already in use");
            return ResponseEntity.ok(response);
        }

        registerRequest.setUserpassword(passwordEncoder.encode(registerRequest.getUserpassword()));
        UserDTO savedUser = userServices.createAccount(registerRequest).getBody();

        ApiResponse<T> response = new ApiResponse<>("User successfully registered", (T) savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
