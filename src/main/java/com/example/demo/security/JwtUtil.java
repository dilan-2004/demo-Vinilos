package com.example.demo.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        byte[] secretBytes = jwtSecret.getBytes();
        if (secretBytes.length < 32) {
            throw new RuntimeException("JWT secret debe tener al menos 32 caracteres");
        }
        this.key = Keys.hmacShaKeyFor(secretBytes);
    }

    public String generateToken(Long userId, String email, String roleName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", roleName = "ADMIN");

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateAndGetClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}