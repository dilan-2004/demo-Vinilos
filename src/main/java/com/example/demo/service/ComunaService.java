package com.example.demo.service;

import com.example.demo.model.Comuna;
import com.example.demo.repository.ComunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    public List<Comuna> obtenerTodas() {
        return comunaRepository.findAll();
    }

    public Optional<Comuna> obtenerPorId(Long id) {
        return comunaRepository.findById(id);
    }

    public Comuna guardarComuna(Comuna comuna) {
        return comunaRepository.save(comuna);
    }
}
