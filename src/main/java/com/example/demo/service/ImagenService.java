package com.example.demo.service;

import com.example.demo.model.Imagen;
import com.example.demo.model.Producto;
import com.example.demo.repository.ImagenRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenService {

    @Autowired
    private ImagenRepository imagenRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public Imagen guardarImagen(Imagen imagen) {
        if (imagen.getProducto() != null && imagen.getProducto().getId() != null) {
            Producto producto = productoRepository.findById(imagen.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            imagen.setProducto(producto);
        }
        return imagenRepository.save(imagen);
    }

    public List<Imagen> obtenerImagenesPorProducto(Long productoId) {
        return imagenRepository.findByProductoId(productoId);
    }

    public Optional<Imagen> obtenerPorId(Long id) {
        return imagenRepository.findById(id);
    }

    public List<Imagen> obtenertodas() {
        return imagenRepository.findAll();
    }

   
}
