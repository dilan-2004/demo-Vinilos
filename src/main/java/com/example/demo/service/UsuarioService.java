package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private com.example.demo.repository.RolRepository rolRepository;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        if (usuario.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        if (usuario.getRol() != null && usuario.getRol().getId() != null) {
            Integer rolId = usuario.getRol().getId();
            var optRol = rolRepository.findById(rolId);
            optRol.ifPresent(usuario::setRol);
        } else {
            rolRepository.findById(2).ifPresentOrElse(usuario::setRol, () -> usuario.setRol(null));
        }

        if (usuario.getId() != null && usuario.getId() == 0L) {
            usuario.setId(null);
        }

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        usuarioActualizado.setId(id);
        return usuarioRepository.save(usuarioActualizado);
    }

    public Optional<Usuario> actualizarParcialmenteUsuario(Long id, Usuario datosActualizados) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            if (datosActualizados.getNombre() != null) {
                usuario.setNombre(datosActualizados.getNombre());
            }
            if (datosActualizados.getEmail() != null) {
                usuario.setEmail(datosActualizados.getEmail());
            }
            if (datosActualizados.getPassword() != null) {
                usuario.setPassword(passwordEncoder.encode(datosActualizados.getPassword()));
            }
            return Optional.of(usuarioRepository.save(usuario));
        }
        return Optional.empty();
    }
}