package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret:defaultsecretkeychangeme}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms:86400000}")
    private long jwtExpirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        try {
            byte[] secretBytes = jwtSecret != null ? jwtSecret.getBytes() : new byte[0];
            if (secretBytes.length < 32) {
                // key too weak for HS256 (needs >= 256 bits / 32 bytes). Generate an ephemeral secure key.
                logger.warn("jwt.secret is too short or missing ({} bytes). Generating an ephemeral JWT key for development.\nSet a secure 'jwt.secret' env var (base64 or long random string) to keep tokens valid across restarts.", secretBytes.length);
                key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            } else {
                key = Keys.hmacShaKeyFor(secretBytes);
            }
        } catch (Exception ex) {
            logger.warn("Failed to initialize JWT key from secret; generating ephemeral key.", ex);
            key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        }
    }

    public String generateToken(Long userId, String email, String roleName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", roleName);
        claims.put("email", email);
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
