package com.example.demo.controller;

import com.example.demo.model.Imagen;
import com.example.demo.service.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

    @Autowired
    private ImagenService imagenService;

    @GetMapping
    public ResponseEntity<List<Imagen>> listarImagenes() {
        return ResponseEntity.ok(imagenService.obtenertodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imagen> obtenerImagen(@PathVariable Long id) {
        return imagenService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Imagen> crearImagen(@RequestBody Imagen imagen) {
        return ResponseEntity.ok(imagenService.guardarImagen(imagen));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Imagen>> obtenerImagenesPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(imagenService.obtenerImagenesPorProducto(productoId));
    }
}