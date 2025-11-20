package com.example.demo.controller;

import com.example.demo.model.VentaProducto;
import com.example.demo.service.VentaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venta-productos")
public class VentaProductoController {

    @Autowired
    private VentaProductoService ventaProductoService;

    @GetMapping
    public ResponseEntity<List<VentaProducto>> listarVentaProductos() {
        return ResponseEntity.ok(ventaProductoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaProducto> obtenerVentaProducto(@PathVariable Long id) {
        return ventaProductoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VentaProducto> crearVentaProducto(@RequestBody VentaProducto ventaProducto) {
        return ResponseEntity.ok(ventaProductoService.guardarVentaProducto(ventaProducto));
    }
}
