package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class); // Logger
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuario() {
        try {
            List<Usuario> usuarios = usuarioService.findAll();
            if (usuarios.isEmpty()) {
                logger.info("No se encontraron usuarios.");
                return ResponseEntity.noContent().build();
            }
            logger.info("Se encontraron {} usuarios.", usuarios.size());
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            logger.error("Error al obtener todos los usuarios", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        try {
            Usuario login = usuarioService.login(usuario);

            if (login != null) {
                login.setContrasena(null);
                logger.info("Login exitoso para el usuario: {}", login.getCorreo());
                return ResponseEntity.ok(login);
            } else {
                logger.warn("Credenciales inválidas para el correo: {}", usuario.getCorreo());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
            }
        } catch (Exception e) {
            logger.error("Error durante el login para el correo: {}", usuario.getCorreo(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            if (usuario == null) {
                logger.warn("Usuario con ID {} no encontrado.", id);
                return ResponseEntity.notFound().build();
            }
            logger.info("Usuario con ID {} encontrado.", id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            logger.error("ID de usuario inválido: {}", id, e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error al obtener usuario por ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        try {
            usuario.setId(null);
            
            Usuario usuarioNew = usuarioService.save(usuario);
            logger.info("Usuario creado exitosamente con ID: {}", usuarioNew.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioNew);
        } catch (RuntimeException e) { 
            logger.error("Error al crear usuario: {}", e.getMessage(), e);
            
            if (e.getMessage().contains("Rol")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) { 
            logger.error("Error inesperado al crear usuario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        try {
            usuario.setId(id);
            Usuario updatedUsuario = usuarioService.save(usuario);
            if (updatedUsuario == null) {
                logger.warn("Usuario con ID {} no encontrado para actualizar.", id);
                return ResponseEntity.notFound().build();
            }
            logger.info("Usuario con ID {} actualizado exitosamente.", id);
            return ResponseEntity.ok(updatedUsuario);
        } catch (RuntimeException e) { 
            logger.error("Error al actualizar usuario con ID {}: {}", id, e.getMessage(), e);
            if (e.getMessage().contains("Rol")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar usuario con ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> updateParcialUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
        try {
            usuario.setId(id);
            Usuario updatedUsuario = usuarioService.partialUpdate(usuario);
            if (updatedUsuario == null) {
                logger.warn("Usuario con ID {} no encontrado para actualización parcial.", id);
                return ResponseEntity.notFound().build();
            }
            logger.info("Usuario con ID {} actualizado parcialmente exitosamente.", id);
            return ResponseEntity.ok(updatedUsuario);
        } catch (RuntimeException e) { 
            logger.error("Error al actualizar parcialmente usuario con ID {}: {}", id, e.getMessage(), e);
            if (e.getMessage().contains("Rol")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            logger.error("Error inesperado al actualizar parcialmente usuario con ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        try {
            usuarioService.deleteById(id);
            logger.info("Usuario con ID {} eliminado exitosamente.", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error al eliminar usuario con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}