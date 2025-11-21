package com.example.demo.service;

import com.example.demo.model.Marca;
import com.example.demo.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> obtenerTodos() {
        return marcaRepository.findAll();
    }

    public Optional<Marca> obtenerPorId(Long id) {
        return marcaRepository.findById(id);
    }

    public Marca guardarMarca(Marca marca) {
        return marcaRepository.save(marca);
    }
    public void eliminarMarca(Long id) {
        marcaRepository.deleteById(id);
    }

    public Marca actualizarMarca(Long id, Marca marcaActualizada) {
        marcaActualizada.setId(id);
        return marcaRepository.save(marcaActualizada);
    }

    public Optional<Marca> actualizarParcialmenteMarca(Long id, Marca datosActualizados) {
        Optional<Marca> marcaExistente = marcaRepository.findById(id);
        if (marcaExistente.isPresent()) {
            Marca marca = marcaExistente.get();
            if (datosActualizados.getNombre() != null) {
                marca.setNombre(datosActualizados.getNombre());
            }
            return Optional.of(marcaRepository.save(marca));
        }
        return Optional.empty();
    }
}
