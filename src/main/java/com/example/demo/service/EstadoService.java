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

    public void eliminarEstado(Long id) {
        estadoRepository.deleteById(id);
    }

    public Estado actualizarEstado(Long id, Estado estadoActualizado) {
        estadoActualizado.setId(id);
        return estadoRepository.save(estadoActualizado);
    }

    public Optional<Estado> actualizarParcialmenteEstado(Long id, Estado datosActualizados) {
        Optional<Estado> estadoExistente = estadoRepository.findById(id);
        if (estadoExistente.isPresent()) {
            Estado estado = estadoExistente.get();
            if (datosActualizados.getNombre() != null) {
                estado.setNombre(datosActualizados.getNombre());
            }
            return Optional.of(estadoRepository.save(estado));
        }
        return Optional.empty();
    }
}
