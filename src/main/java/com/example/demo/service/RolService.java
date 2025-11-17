package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Rol;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.model.Usuario;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RolService {

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    @SuppressWarnings("null")
    public Rol findById(Integer id) {
        return rolRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        List<Usuario> usuariosConEsteRol = usuarioRepository.findByRolId(id);
    if (!usuariosConEsteRol.isEmpty()) {
        throw new RuntimeException("No se puede eliminar el rol con ID " + id + " porque hay " + usuariosConEsteRol.size() + " usuario(s) asociado(s).");
    }
    rolRepository.deleteById(id);
}

    @SuppressWarnings("null")
    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }
}