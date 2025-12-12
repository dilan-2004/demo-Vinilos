package com.example.demo.service;

import com.example.demo.model.VentaProducto;
import com.example.demo.repository.VentaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaProductoService {

    @Autowired
    private VentaProductoRepository ventaProductoRepository;

    public List<VentaProducto> obtenerTodos() {
        return ventaProductoRepository.findAll();
    }

    public Optional<VentaProducto> obtenerPorId(Long id) {
        return ventaProductoRepository.findById(id);
    }

    public VentaProducto guardarVentaProducto(VentaProducto ventaProducto) {
        return ventaProductoRepository.save(ventaProducto);
    }

    public void eliminarVentaProducto(Long id) {
        ventaProductoRepository.deleteById(id);
    }

    public VentaProducto actualizarVentaProducto(Long id, VentaProducto ventaProductoActualizado) {
        ventaProductoActualizado.setId(id);
        return ventaProductoRepository.save(ventaProductoActualizado);
    }

    public Optional<VentaProducto> actualizarParcialmenteVentaProducto(Long id, VentaProducto datosActualizados) {
        Optional<VentaProducto> ventaProductoExistente = ventaProductoRepository.findById(id);
        if (ventaProductoExistente.isPresent()) {
            VentaProducto ventaProducto = ventaProductoExistente.get();
            if (datosActualizados.getCantidad() != null) {
                ventaProducto.setCantidad(datosActualizados.getCantidad());
            }
            if (datosActualizados.getPrecioUnitario() != null) {
                ventaProducto.setPrecioUnitario(datosActualizados.getPrecioUnitario());
            }
            return Optional.of(ventaProductoRepository.save(ventaProducto));
        }
        return Optional.empty();
    }
}
