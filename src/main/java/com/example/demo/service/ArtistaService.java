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

    public void eliminarArtista(Long id) {
        artistaRepository.deleteById(id);
    }

    public Artista actualizarArtista(Long id, Artista artistaActualizado) {
        artistaActualizado.setId(id);
        return artistaRepository.save(artistaActualizado);
    }

    public Optional<Artista> actualizarParcialmenteArtista(Long id, Artista datosActualizados) {
        Optional<Artista> artistaExistente = artistaRepository.findById(id);
        if (artistaExistente.isPresent()) {
            Artista artista = artistaExistente.get();
            if (datosActualizados.getNombre() != null) {
                artista.setNombre(datosActualizados.getNombre());
            }
            if (datosActualizados.getBiografia() != null) {
                artista.setBiografia(datosActualizados.getBiografia());
            }
            return Optional.of(artistaRepository.save(artista));
        }
        return Optional.empty();
    }
}
