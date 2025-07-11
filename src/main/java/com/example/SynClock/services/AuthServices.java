package com.example.SynClock.services;

import com.example.SynClock.model.ApiResponse;
import com.example.SynClock.model.DTOs.CreateUserDTO;
import com.example.SynClock.model.DTOs.UserDTO;
import com.example.SynClock.security.JwtGenerate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {
    private final AuthenticationManager authenticationManager;
    private final JwtGenerate jwtGenerate;
    private final UserServices userServices;
    private final PasswordEncoder passwordEncoder;

    public AuthServices(AuthenticationManager authenticationManager, JwtGenerate jwtGenerate, UserServices userServices, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerate = jwtGenerate;
        this.userServices = userServices;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<ApiResponse<String>> login(CreateUserDTO loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getUserpassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtGenerate.generateJwtToken(authentication);

            ApiResponse<String> response = new ApiResponse<>("Login successful", jwtToken);
            return ResponseEntity.ok(response);
        }
        catch (AuthenticationException e) {
            String message;
            HttpStatus status;

            if (e instanceof BadCredentialsException) {
                message = "Invalid username or password";
                status = HttpStatus.UNAUTHORIZED;
            }
//            Non-used Exception types
//            else if (e instanceof DisabledException) {
//                message = "User is disabled";
//                status = HttpStatus.FORBIDDEN;
//            } else if (e instanceof LockedException) {
//                message = "User account is locked";
//                status = HttpStatus.LOCKED;
//            }
            else {
                message = "Authentication failed";
                status = HttpStatus.UNAUTHORIZED;
            }
            return ResponseEntity
                    .status(status)
                    .body(new ApiResponse<>(message, null));
        }


    }

    public <T> ResponseEntity<ApiResponse<T>> register(CreateUserDTO registerRequest) {
        if (userServices.getOneUserByUserName(registerRequest.getUsername()) != null) {
            ApiResponse<T> response = new ApiResponse<>("Username already in use");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        registerRequest.setUserpassword(passwordEncoder.encode(registerRequest.getUserpassword()));
        UserDTO savedUser = userServices.createAccount(registerRequest).getBody();

        ApiResponse<T> response = new ApiResponse<>("User successfully registered", (T) savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
