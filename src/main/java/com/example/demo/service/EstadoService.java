package com.example.demo.service;

import com.example.demo.model.Estado;
import com.example.demo.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> obtenerTodos() {
        return estadoRepository.findAll();
    }

    public Optional<Estado> obtenerPorId(Long id) {
        return estadoRepository.findById(id);
    }

    public Estado guardarEstado(Estado estado) {
        return estadoRepository.save(estado);
    }
}
