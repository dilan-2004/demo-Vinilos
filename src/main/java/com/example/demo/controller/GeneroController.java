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
}
