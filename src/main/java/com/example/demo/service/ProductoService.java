package com.example.demo.service;

import com.example.demo.model.Artista;
import com.example.demo.model.Genero;
import com.example.demo.model.Marca;
import com.example.demo.model.Producto;
import com.example.demo.repository.ArtistaRepository;
import com.example.demo.repository.GeneroRepository;
import com.example.demo.repository.MarcaRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardarProducto(Producto producto) {
        // Asociar artistas
        if (producto.getArtistas() != null && !producto.getArtistas().isEmpty()) {
            List<Artista> artistasGuardados = new ArrayList<>();
            for (Artista artista : producto.getArtistas()) {
                Artista encontrado = artistaRepository.findById(artista.getId())
                        .orElseThrow(() -> new RuntimeException("Artista no encontrado"));
                artistasGuardados.add(encontrado);
            }
            producto.setArtistas(artistasGuardados);
        }

        // Asociar género
        if (producto.getGenero() != null && producto.getGenero().getId() > 0) {
            Genero genero = generoRepository.findById(producto.getGenero().getId())
                    .orElseThrow(() -> new RuntimeException("Género no encontrado"));
            producto.setGenero(genero);
        }

        // Asociar marca
        if (producto.getMarca() != null && producto.getMarca().getId() > 0) {
            Marca marca = marcaRepository.findById(producto.getMarca().getId())
                    .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
            producto.setMarca(marca);
        }

        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        productoActualizado.setId(id);
        // Asociar artistas
        if (productoActualizado.getArtistas() != null && !productoActualizado.getArtistas().isEmpty()) {
            List<Artista> artistasGuardados = new ArrayList<>();
            for (Artista artista : productoActualizado.getArtistas()) {
                Artista encontrado = artistaRepository.findById(artista.getId())
                        .orElseThrow(() -> new RuntimeException("Artista no encontrado"));
                artistasGuardados.add(encontrado);
            }
            productoActualizado.setArtistas(artistasGuardados);
        }

        // Asociar género
        if (productoActualizado.getGenero() != null && productoActualizado.getGenero().getId() > 0) {
            Genero genero = generoRepository.findById(productoActualizado.getGenero().getId())
                    .orElseThrow(() -> new RuntimeException("Género no encontrado"));
            productoActualizado.setGenero(genero);
        }

        // Asociar marca
        if (productoActualizado.getMarca() != null && productoActualizado.getMarca().getId() > 0) {
            Marca marca = marcaRepository.findById(productoActualizado.getMarca().getId())
                    .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
            productoActualizado.setMarca(marca);
        }

        return productoRepository.save(productoActualizado);
    }

    public Optional<Producto> actualizarParcialmenteProducto(Long id, Producto datosActualizados) {
        Optional<Producto> productoExistente = productoRepository.findById(id);
        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            if (datosActualizados.getTitulo() != null) {
                producto.setTitulo(datosActualizados.getTitulo());
            }
            if (datosActualizados.getDescripcion() != null) {
                producto.setDescripcion(datosActualizados.getDescripcion());
            }
            if (datosActualizados.getPrecio() != null) {
                producto.setPrecio(datosActualizados.getPrecio());
            }
            if (datosActualizados.getStock() != null) {
                producto.setStock(datosActualizados.getStock());
            }
            // Actualizar marca si se envía
            if (datosActualizados.getMarca() != null && datosActualizados.getMarca().getId() > 0) {
                Marca marca = marcaRepository.findById(datosActualizados.getMarca().getId())
                        .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
                producto.setMarca(marca);
            }
            // Actualizar género si se envía
            if (datosActualizados.getGenero() != null && datosActualizados.getGenero().getId() > 0) {
                Genero genero = generoRepository.findById(datosActualizados.getGenero().getId())
                        .orElseThrow(() -> new RuntimeException("Género no encontrado"));
                producto.setGenero(genero);
            }
            // Actualizar artistas si se envían
            if (datosActualizados.getArtistas() != null && !datosActualizados.getArtistas().isEmpty()) {
                List<Artista> artistasGuardados = new ArrayList<>();
                for (Artista artista : datosActualizados.getArtistas()) {
                    Artista encontrado = artistaRepository.findById(artista.getId())
                            .orElseThrow(() -> new RuntimeException("Artista no encontrado"));
                    artistasGuardados.add(encontrado);
                }
                producto.setArtistas(artistasGuardados);
            }
            return Optional.of(productoRepository.save(producto));
        }
        return Optional.empty();
    }
}