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
}
