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
}