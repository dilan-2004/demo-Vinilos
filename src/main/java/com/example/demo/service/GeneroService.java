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
    public void eliminarGenero(Long id) {
        generoRepository.deleteById(id);
    }

    public Genero actualizarGenero(Long id, Genero generoActualizado) {
        generoActualizado.setId(id);
        return generoRepository.save(generoActualizado);
    }

    public Optional<Genero> actualizarParcialmenteGenero(Long id, Genero datosActualizados) {
        Optional<Genero> generoExistente = generoRepository.findById(id);
        if (generoExistente.isPresent()) {
            Genero genero = generoExistente.get();
            if (datosActualizados.getNombre() != null) {
                genero.setNombre(datosActualizados.getNombre());
            }
            return Optional.of(generoRepository.save(genero));
        }
        return Optional.empty();
    }
}
