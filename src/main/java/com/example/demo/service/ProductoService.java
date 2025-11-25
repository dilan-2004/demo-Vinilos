package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Transactional
    public Producto guardarProducto(Producto producto) {
        manejarRelaciones(producto);
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Transactional
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        productoActualizado.setId(id);
        manejarRelaciones(productoActualizado);
        return productoRepository.save(productoActualizado);
    }

    @Transactional
    public Optional<Producto> actualizarParcialmenteProducto(Long id, Producto datosActualizados) {
        Optional<Producto> existenteOpt = productoRepository.findById(id);
        if (existenteOpt.isEmpty()) return Optional.empty();

        Producto existente = existenteOpt.get();

        // Actualizar campos simples
        if (datosActualizados.getTitulo() != null) existente.setTitulo(datosActualizados.getTitulo());
        if (datosActualizados.getDescripcion() != null) existente.setDescripcion(datosActualizados.getDescripcion());
        if (datosActualizados.getPrecio() != null) existente.setPrecio(datosActualizados.getPrecio());
        if (datosActualizados.getStock() != null) existente.setStock(datosActualizados.getStock());
        if (datosActualizados.getImagenUrl() != null) existente.setImagenUrl(datosActualizados.getImagenUrl());

        if (datosActualizados.getArtista() != null) {
            existente.setArtista(datosActualizados.getArtista());
            manejarArtista(existente);
        }
        if (datosActualizados.getMarca() != null) {
            existente.setMarca(datosActualizados.getMarca());
            manejarMarca(existente);
        }
        if (datosActualizados.getGenero() != null) {
            existente.setGenero(datosActualizados.getGenero());
            manejarGenero(existente);
        }

        return Optional.of(productoRepository.save(existente));
    }

    private void manejarRelaciones(Producto producto) {
        manejarArtista(producto);
        manejarMarca(producto);
        manejarGenero(producto);
    }

    private void manejarArtista(Producto producto) {
        if (producto.getArtista() != null) {
            if (producto.getArtista().getId() == null) {
                Artista existente = artistaRepository.findByNombre(producto.getArtista().getNombre());
                if (existente != null) {
                    producto.setArtista(existente);
                }
            }
        }
    }

    private void manejarMarca(Producto producto) {
        if (producto.getMarca() != null && producto.getMarca().getId() == null) {
            Marca existente = marcaRepository.findByNombre(producto.getMarca().getNombre());
            if (existente != null) {
                producto.setMarca(existente);
            }
        }
    }

    private void manejarGenero(Producto producto) {
        if (producto.getGenero() != null && producto.getGenero().getId() == null) {
            Genero existente = generoRepository.findByNombre(producto.getGenero().getNombre());
            if (existente != null) {
                producto.setGenero(existente);
            }
        }
    }
}