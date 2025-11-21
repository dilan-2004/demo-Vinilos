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
    public void eliminarComuna(Long id) {
        comunaRepository.deleteById(id);
    }

    public Comuna actualizarComuna(Long id, Comuna comunaActualizada) {
        comunaActualizada.setId(id);
        return comunaRepository.save(comunaActualizada);
    }

    public Optional<Comuna> actualizarParcialmenteComuna(Long id, Comuna datosActualizados) {
        Optional<Comuna> comunaExistente = comunaRepository.findById(id);
        if (comunaExistente.isPresent()) {
            Comuna comuna = comunaExistente.get();
            if (datosActualizados.getNombre() != null) {
                comuna.setNombre(datosActualizados.getNombre());
            }
            return Optional.of(comunaRepository.save(comuna));
        }
        return Optional.empty();
    }
}
