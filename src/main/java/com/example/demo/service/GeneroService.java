package com.example.demo.service;

import com.example.demo.model.Genero;
import com.example.demo.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    public List<Genero> obtenerTodos() {
        return generoRepository.findAll();
    }

    public Optional<Genero> obtenerPorId(Long id) {
        return generoRepository.findById(id);
    }

    public Genero guardarGenero(Genero genero) {
        return generoRepository.save(genero);
    }
}
