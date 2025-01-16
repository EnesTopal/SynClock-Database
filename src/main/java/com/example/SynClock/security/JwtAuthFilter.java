package com.example.SynClock.security;

import com.example.SynClock.services.JwtUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtGenerate jwtGenerate;
    private final JwtUserDetailsService jwtUserDetailsService;

    public JwtAuthFilter(JwtGenerate jwtGenerate, JwtUserDetailsService jwtUserDetailsService) {
        this.jwtGenerate = jwtGenerate;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Token çıkarılır
            String jwt = extractToken(request);

            if (StringUtils.hasText(jwt)) {
                System.out.println("Token alındı: " + jwt);

                // Token doğrulama
                if (jwtGenerate.validateToken(jwt)) {
                    System.out.println("Token geçerli.");
                    Long id = jwtGenerate.getUserIdFromToken(jwt);
                    UserDetails user = jwtUserDetailsService.loadUserByid(id);

                    // Kullanıcı doğrulama
                    if (user != null) {
                        System.out.println("Kullanıcı doğrulandı: " + user.getUsername());

                        // SecurityContext'e kimlik doğrulama bilgisi eklenir
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    } else {
                        System.out.println("Geçerli token ama kullanıcı bulunamadı.");
                        SecurityContextHolder.clearContext();
                    }
                } else {
                    System.out.println("Geçersiz token.");
                    SecurityContextHolder.clearContext();
                }
            } else {
                System.out.println("Token bulunamadı.");
                SecurityContextHolder.clearContext();
            }
        } catch (Exception e) {
            // Tüm hatalarda SecurityContext temizlenir
            SecurityContextHolder.clearContext();
            System.out.println("Token doğrulama sırasında hata oluştu: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Filtre zinciri devam eder
            filterChain.doFilter(request, response);
        }
    }



    private String extractToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring("Bearer".length() + 1);
        }
        return null;
    }
}
