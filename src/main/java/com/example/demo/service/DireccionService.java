package com.example.demo.service;

import com.example.demo.model.Direccion;
import com.example.demo.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    public List<Direccion> obtenerTodas() {
        return direccionRepository.findAll();
    }

    public Optional<Direccion> obtenerPorId(Long id) {
        return direccionRepository.findById(id);
    }

    public Direccion guardarDireccion(Direccion direccion) {
        return direccionRepository.save(direccion);
    }
}
