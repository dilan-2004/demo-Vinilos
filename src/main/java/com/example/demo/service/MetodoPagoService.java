package com.example.demo.service;

import com.example.demo.model.MetodoPago;
import com.example.demo.repository.MetodoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoPagoService {

    @Autowired
    private MetodoPagoRepository metodoPagoRepository;

    public List<MetodoPago> obtenerTodos() {
        return metodoPagoRepository.findAll();
    }

    public Optional<MetodoPago> obtenerPorId(Long id) {
        return metodoPagoRepository.findById(id);
    }

    public MetodoPago guardarMetodoPago(MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    public void eliminarMetodoPago(Long id) {
        metodoPagoRepository.deleteById(id);
    }

    public MetodoPago actualizarMetodoPago(Long id, MetodoPago metodoPagoActualizado) {
        metodoPagoActualizado.setId(id);
        return metodoPagoRepository.save(metodoPagoActualizado);
    }

    public Optional<MetodoPago> actualizarParcialmenteMetodoPago(Long id, MetodoPago datosActualizados) {
        Optional<MetodoPago> metodoPagoExistente = metodoPagoRepository.findById(id);
        if (metodoPagoExistente.isPresent()) {
            MetodoPago metodoPago = metodoPagoExistente.get();
            if (datosActualizados.getNombre() != null) {
                metodoPago.setNombre(datosActualizados.getNombre());
            }
            return Optional.of(metodoPagoRepository.save(metodoPago));
        }
        return Optional.empty();
    }
}