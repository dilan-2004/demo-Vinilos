package com.example.demo.service;

import com.example.demo.model.Artista;
import com.example.demo.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    public List<Artista> obtenerTodos() {
        return artistaRepository.findAll();
    }

    public Optional<Artista> obtenerPorId(Long id) {
        return artistaRepository.findById(id);
    }

    public Artista guardarArtista(Artista artista) {
        return artistaRepository.save(artista);
    }
}
