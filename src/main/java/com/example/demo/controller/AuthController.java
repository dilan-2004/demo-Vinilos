package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            String password = payload.get("password");
            
            if (email == null || password == null) {
                System.out.println("DEBUG: Email o password faltantes");
                return ResponseEntity.badRequest().body(Map.of("error", "Email y contraseña requeridos"));
            }

            Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
            if (usuarioOpt.isEmpty()) {
                System.out.println("DEBUG: Usuario no encontrado: " + email);
                return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
            }

            Usuario usuario = usuarioOpt.get();
            String roleName = "USER";
            
            if (usuario.getRol() != null && usuario.getRol().getNombre() != null) {
                roleName = usuario.getRol().getNombre();
            }
            System.out.println("DEBUG: Rol detectado: " + roleName);

            if (!passwordEncoder.matches(password, usuario.getPassword())) {
                System.out.println("DEBUG: Contraseña incorrecta para: " + email);
                return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
            }

            String token = jwtUtil.generateToken(usuario.getId(), email, roleName);
            System.out.println("DEBUG: Login exitoso para: " + email);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", Map.of(
                "id", usuario.getId(),
                "email", email,
                "rol", roleName
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.out.println("ERROR FATAL EN LOGIN:");
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Error interno"));
        }
    }
}