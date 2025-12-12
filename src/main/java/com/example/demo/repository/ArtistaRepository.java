package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Artista;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    Artista findByNombre(String nombre);
}
