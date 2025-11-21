package com.example.demo.controller;

import com.example.demo.model.Marca;
import com.example.demo.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<Marca>> listarMarcas() {
        return ResponseEntity.ok(marcaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> obtenerMarca(@PathVariable Long id) {
        return marcaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Marca> crearMarca(@RequestBody Marca marca) {
        return ResponseEntity.ok(marcaService.guardarMarca(marca));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMarca(@PathVariable Long id) {
        marcaService.eliminarMarca(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> actualizarMarca(@PathVariable Long id, @RequestBody Marca marcaActualizada) {
        return marcaService.obtenerPorId(id)
                .map(marca -> ResponseEntity.ok(marcaService.actualizarMarca(id, marcaActualizada)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Marca> actualizarParcialmenteMarca(@PathVariable Long id, @RequestBody Marca datosActualizados) {
        return marcaService.actualizarParcialmenteMarca(id, datosActualizados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}