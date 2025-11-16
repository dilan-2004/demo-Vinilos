package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.model.Rol;
import com.example.demo.repository.RolRepository;


@Service
@Transactional 
@SuppressWarnings("null")
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class); 

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    @Autowired
    private ProductService productService;

    public List<Usuario> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    public Usuario findById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setContrasena(null);
        }
        return usuario;
    }

    public Usuario login(Usuario usuario) {
        Usuario foundUsuario = usuarioRepository.findByCorreo(usuario.getCorreo());
 
        if (foundUsuario != null &&  passwordEncoder.matches(usuario.getContrasena(), foundUsuario.getContrasena())) {
            return foundUsuario;
        }
        logger.warn("Intento de login fallido para el correo: {}", usuario.getCorreo());
        return null;
    }

    public Usuario updateUsuario(Usuario usuario) {
        return save(usuario); 
    }

    public Usuario save(Usuario usuario) {
        
        if (usuario.getRol() == null || usuario.getRol().getNombre() == null || usuario.getRol().getNombre().trim().isEmpty()) {
             logger.error("Rol no proporcionado o nombre del rol vacío al intentar guardar usuario.");
             throw new RuntimeException("Rol no proporcionado o nombre del rol vacío.");
        }

        String nombreRol = usuario.getRol().getNombre();

                Optional<Rol> rolOptional = rolRepository.findByNombre(nombreRol);
        if (rolOptional.isEmpty()) {
            logger.error("Rol con nombre '{}' no encontrado al intentar guardar usuario.", nombreRol);
                    throw new RuntimeException("Rol con nombre '" + nombreRol + "' no encontrado.");
        }
        Rol rolAsociado = rolOptional.get();

                usuario.setRol(rolAsociado);

        
        String passwordHasheada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(passwordHasheada);

        
        try {
            logger.info("Guardando/actualizando usuario con rol: {}", rolAsociado.getNombre());
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            logger.error("Error al guardar usuario en la base de datos", e);
            throw e;
        }
    }


    public Usuario partialUpdate(Usuario usuario){
        Integer id = usuario.getId();
        Optional<Usuario> existingUsuarioOpt = usuarioRepository.findById(id);

        if (existingUsuarioOpt.isEmpty()) {
            logger.warn("Intento de actualización parcial fallido: Usuario con ID {} no encontrado.", id);
            return null;
        }

        Usuario existingUsuario = existingUsuarioOpt.get();

        if (usuario.getNombre() != null) {
            existingUsuario.setNombre(usuario.getNombre());
        }
        if (usuario.getCorreo() != null) {
            existingUsuario.setCorreo(usuario.getCorreo());
        }

        if(usuario.getContrasena() != null) {
            existingUsuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }

        
        if(usuario.getRol() != null) {
            if (usuario.getRol().getNombre() == null || usuario.getRol().getNombre().trim().isEmpty()) {
                logger.error("Nombre del rol vacío en actualización parcial para usuario ID: {}", id);
                throw new RuntimeException("Nombre del rol vacío en actualización parcial.");
            }
            String nombreRol = usuario.getRol().getNombre();
            Optional<Rol> rolOptional = rolRepository.findByNombre(nombreRol);
            if (rolOptional.isEmpty()) {
                logger.error("Rol con nombre '{}' no encontrado al intentar actualizar parcialmente usuario con ID {}.", nombreRol, id);
                throw new RuntimeException("Rol con nombre '" + nombreRol + "' no encontrado.");
            }
            Rol rolAsociado = rolOptional.get();
            existingUsuario.setRol(rolAsociado);
        }

        try {
            logger.info("Actualizando parcialmente usuario con ID: {}", id);
            return usuarioRepository.save(existingUsuario);
        } catch (Exception e) {
            logger.error("Error al actualizar parcialmente usuario con ID: {}", id, e);
            throw e;
        }
    }

    public void deleteById(Integer id) {
        
        if (!usuarioRepository.existsById(id)) {
             logger.warn("Intento de eliminación fallido: Usuario con ID {} no encontrado.", id);
                }
        logger.info("Eliminando usuario con ID: {}", id);
        productService.deleteByUsuarioId(id);
        usuarioRepository.deleteById(id);
    }
}