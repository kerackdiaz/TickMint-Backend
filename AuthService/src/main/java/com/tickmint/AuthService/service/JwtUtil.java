package com.tickmint.AuthService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtil {

    private String secret;
    private String expiration;

    private static SecretKey SECRET_KEY;

    @PostConstruct
    public void init() {


        String envSecret = System.getenv("jwt.secret");
        if (envSecret != null && !envSecret.isEmpty()) {
            secret = envSecret;
        }

        if (secret == null || secret.isEmpty()) {
            throw new IllegalStateException("jwt.secret is missing in both environment variables and application.properties");
        }


        SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes());

        String expirationTime = System.getenv("jwt.expiration");
        if (expirationTime != null && !expirationTime.isEmpty()) {
            expiration = expirationTime;
        }
        if (expiration == null || expiration.isEmpty()) {
            throw new IllegalStateException("jwt.expiration is missing in both environment variables and application.properties");
        }
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String userId, String role, String email) {
        return Jwts.builder()
                .subject(userId)
                .claim("role", role)
                .claim("email", email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(SECRET_KEY)
                .compact();
    }
}
