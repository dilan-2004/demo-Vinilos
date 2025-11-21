package com.example.demo.service;

import com.example.demo.model.Direccion;
import com.example.demo.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    public List<Direccion> obtenerTodas() {
        return direccionRepository.findAll();
    }

    public Optional<Direccion> obtenerPorId(Long id) {
        return direccionRepository.findById(id);
    }

    public Direccion guardarDireccion(Direccion direccion) {
        return direccionRepository.save(direccion);
    }
    public void eliminarDireccion(Long id) {
        direccionRepository.deleteById(id);
    }

    public Direccion actualizarDireccion(Long id, Direccion direccionActualizada) {
        direccionActualizada.setId(id);
        return direccionRepository.save(direccionActualizada);
    }

    public Optional<Direccion> actualizarParcialmenteDireccion(Long id, Direccion datosActualizados) {
        Optional<Direccion> direccionExistente = direccionRepository.findById(id);
        if (direccionExistente.isPresent()) {
            Direccion direccion = direccionExistente.get();
            if (datosActualizados.getCalle() != null) {
                direccion.setCalle(datosActualizados.getCalle());
            }
            if (datosActualizados.getNumero() != null) {
                direccion.setNumero(datosActualizados.getNumero());
            }
                if (datosActualizados.getComuna() != null) {
                    direccion.setComuna(datosActualizados.getComuna());
                }
            return Optional.of(direccionRepository.save(direccion));
        }
        return Optional.empty();
    }
}
