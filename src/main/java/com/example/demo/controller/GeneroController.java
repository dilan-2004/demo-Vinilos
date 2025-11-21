package com.example.demo.controller;

import com.example.demo.model.Genero;
import com.example.demo.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generos")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public ResponseEntity<List<Genero>> listarGeneros() {
        return ResponseEntity.ok(generoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genero> obtenerGenero(@PathVariable Long id) {
        return generoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Genero> crearGenero(@RequestBody Genero genero) {
        return ResponseEntity.ok(generoService.guardarGenero(genero));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGenero(@PathVariable Long id) {
        generoService.eliminarGenero(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genero> actualizarGenero(@PathVariable Long id, @RequestBody Genero generoActualizado) {
        return generoService.obtenerPorId(id)
                .map(genero -> ResponseEntity.ok(generoService.actualizarGenero(id, generoActualizado)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Genero> actualizarParcialmenteGenero(@PathVariable Long id, @RequestBody Genero datosActualizados) {
        return generoService.actualizarParcialmenteGenero(id, datosActualizados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
