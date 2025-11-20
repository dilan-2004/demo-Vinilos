package com.example.demo.controller;

import com.example.demo.model.MetodoPago;
import com.example.demo.service.MetodoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metodos-pago")
public class MetodoPagoController {

    @Autowired
    private MetodoPagoService metodoPagoService;

    @GetMapping
    public ResponseEntity<List<MetodoPago>> listarMetodosPago() {
        return ResponseEntity.ok(metodoPagoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoPago> obtenerMetodoPago(@PathVariable Long id) {
        return metodoPagoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MetodoPago> crearMetodoPago(@RequestBody MetodoPago metodoPago) {
        return ResponseEntity.ok(metodoPagoService.guardarMetodoPago(metodoPago));
    }
}
