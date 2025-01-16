package com.example.SynClock.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;




import java.util.Date;
import java.util.UUID;

@Component
public class JwtGenerate {
    @Value("${synclock.app.secret}")
    private String APP_SECRET;
    @Value("${synclock.expires.in}")
    private long EXPIRES_IN;


    public String generateJwtToken(Authentication authentication){
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getUuid()))
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET)
                .compact();
    }

    public Long getUserIdFromToken(String token){
        Claims claims =  (Claims) Jwts.parser()
                .setSigningKey(APP_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Incorrect token format.");
        } catch (SignatureException e) {
            System.out.println("Token signature is invalid.");
        } catch (IllegalArgumentException e) {
            System.out.println("Empty token.");
        }
        return false;
    }

    private boolean isTokenExpired(String token) {
        Date expire = (Date) Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return expire.before(new Date());
    }

}
