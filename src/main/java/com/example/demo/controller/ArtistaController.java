package com.example.demo.controller;

import com.example.demo.model.Artista;
import com.example.demo.service.ArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artistas")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @GetMapping
    public ResponseEntity<List<Artista>> listarArtistas() {
        return ResponseEntity.ok(artistaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artista> obtenerArtista(@PathVariable Long id) {
        return artistaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Artista> crearArtista(@RequestBody Artista artista) {
        return ResponseEntity.ok(artistaService.guardarArtista(artista));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArtista(@PathVariable Long id) {
        artistaService.eliminarArtista(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artista> actualizarArtista(@PathVariable Long id, @RequestBody Artista artistaActualizado) {
        return artistaService.obtenerPorId(id)
                .map(artista -> ResponseEntity.ok(artistaService.actualizarArtista(id, artistaActualizado)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Artista> actualizarParcialmenteArtista(@PathVariable Long id, @RequestBody Artista datosActualizados) {
        return artistaService.actualizarParcialmenteArtista(id, datosActualizados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
