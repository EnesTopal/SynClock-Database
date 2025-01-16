package com.example.SynClock.services;

import com.example.SynClock.repositories.UserRepository;
import com.example.SynClock.security.JwtGenerate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class UserTokenServices {
    private final JwtGenerate jwtGenerate;
    private final HttpServletRequest request;
    private final UserRepository userRepository;

    public UserTokenServices(JwtGenerate jwtGenerate,
                             HttpServletRequest request,
                             UserRepository userRepository) {
        this.jwtGenerate = jwtGenerate;
        this.request = request;
        this.userRepository = userRepository;
    }

    public Long validateAndExtractUserId() {
        String token = extractToken(request);
        if (token != null && jwtGenerate.validateToken(token)) {
            return jwtGenerate.getUserIdFromToken(token);
        }
        return null;
    }

    private String extractToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
