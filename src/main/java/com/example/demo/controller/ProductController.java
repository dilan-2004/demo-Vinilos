package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products") // Puedes cambiar a /api/usuarios/{id}/productos si prefieres anidamiento
@Tag(name = "Product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            logger.info("Se encontraron {} productos.", products.size());
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error al obtener todos los productos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Get all products for a specific user")
    public ResponseEntity<List<Product>> getProductsByUsuarioId(@PathVariable Integer usuarioId) {
        try {
            List<Product> products = productService.getProductsByUsuarioId(usuarioId);
            logger.info("Se encontraron {} productos para el usuario ID {}.", products.size(), usuarioId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            logger.error("Error al obtener productos del usuario ID: {}", usuarioId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by its ID")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            if (product == null) {
                logger.warn("Producto con ID {} no encontrado.", id);
                return ResponseEntity.notFound().build();
            }
            logger.info("Producto con ID {} encontrado.", id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            logger.error("Error al obtener producto por ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/usuario/{usuarioId}")
    @Operation(summary = "Create a new product and associate it with a user")
    public ResponseEntity<Product> createProduct(@PathVariable Integer usuarioId, @RequestBody Product product) {
        try {
            Product savedProduct = productService.saveProduct(product, usuarioId);
            logger.info("Producto '{}' creado y asociado al usuario ID {} exitosamente.", product.getNombre(), usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (RuntimeException e) { 
            logger.error("Error al crear producto: {}", e.getMessage(), e);
            if (e.getMessage().contains("Usuario")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            logger.error("Error inesperado al crear producto", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            if (updatedProduct == null) {
                logger.warn("Producto con ID {} no encontrado para actualizar.", id);
                return ResponseEntity.notFound().build();
            }
            logger.info("Producto con ID {} actualizado exitosamente.", id);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            logger.error("Error al actualizar producto con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by its ID")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            logger.info("Producto con ID {} eliminado exitosamente.", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error al eliminar producto con ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}