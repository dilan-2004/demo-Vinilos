package com.example.demo.controller;

import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "email and password required"));
        }

        return usuarioRepository.findByEmail(email)
                .map(usuario -> {
                    if (passwordEncoder.matches(password, usuario.getPassword())) {
                        String token = jwtUtil.generateToken(usuario.getId(), usuario.getEmail(), usuario.getRol() != null ? usuario.getRol().getNombre() : "USER");
                        Map<String, Object> resp = new HashMap<>();
                        resp.put("token", token);
                        resp.put("user", usuario);
                        return ResponseEntity.ok(resp);
                    } else {
                        return ResponseEntity.status(401).body(Map.of("error", "invalid credentials"));
                    }
                })
                .orElseGet(() -> ResponseEntity.status(401).body(Map.of("error", "invalid credentials")));
    }
}
