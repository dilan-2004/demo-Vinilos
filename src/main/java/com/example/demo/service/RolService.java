package com.example.demo.service;

import com.example.demo.model.Rol;
import com.example.demo.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> obtenerTodos() {
        return rolRepository.findAll();
    }

    public Optional<Rol> obtenerPorId(Integer id) {
        return rolRepository.findById(id);
    }

    public Rol guardarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public void eliminarRol(Integer id) {
        rolRepository.deleteById(id);
    }

    public Rol actualizarRol(Integer id, Rol rolActualizado) {
        rolActualizado.setId(id);
        return rolRepository.save(rolActualizado);
    }

    public Optional<Rol> actualizarParcialmenteRol(Integer id, Rol datosActualizados) {
        Optional<Rol> rolExistente = rolRepository.findById(id);
        if (rolExistente.isPresent()) {
            Rol rol = rolExistente.get();
            if (datosActualizados.getNombre() != null) {
                rol.setNombre(datosActualizados.getNombre());
            }
            return Optional.of(rolRepository.save(rol));
        }
        return Optional.empty();
    }
}