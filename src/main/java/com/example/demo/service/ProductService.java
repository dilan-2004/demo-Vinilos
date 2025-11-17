package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UsuarioRepository; // Importar UsuarioRepository
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Usar el de Spring

import java.util.List;
import java.util.Optional;

@Service
@Transactional // Usar el de Spring
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // Inyectar UsuarioRepository

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByUsuarioId(Integer usuarioId) {
        logger.info("Obteniendo productos para el usuario ID: {}", usuarioId);
        return productRepository.findByUsuarioId(usuarioId);
    }

    public Product getProductById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            logger.warn("Producto con ID {} no encontrado.", id);
        }
        return productOpt.orElse(null);
    }

    // Método para crear un producto y asociarlo a un usuario
    public Product saveProduct(Product product, Integer usuarioId) {
        logger.info("Guardando producto '{}' y asociándolo al usuario ID: {}", product.getNombre(), usuarioId);

        // Buscar el usuario en la base de datos
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            logger.error("Usuario con ID {} no encontrado al intentar guardar producto.", usuarioId);
            throw new RuntimeException("Usuario con ID " + usuarioId + " no encontrado.");
        }
        Usuario usuarioAsociado = usuarioOpt.get();

        // Asociar el usuario al producto
        product.setUsuario(usuarioAsociado);

        try {
            logger.info("Guardando producto en la base de datos...");
            return productRepository.save(product);
        } catch (Exception e) {
            logger.error("Error al guardar producto en la base de datos", e);
            throw e;
        }
    }

    // Método para actualizar un producto (la asociación de usuario generalmente no cambia en una actualización simple)
    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();
            // Actualizar solo los campos permitidos
            existingProduct.setNombre(productDetails.getNombre());
            existingProduct.setPrecio(productDetails.getPrecio());
            existingProduct.setAutor(productDetails.getAutor());
            existingProduct.setDescripcion(productDetails.getDescripcion());
            existingProduct.setImg(productDetails.getImg());
            // No se actualiza el usuario asociado aquí a menos que sea explícito

            logger.info("Actualizando producto con ID: {}", id);
            return productRepository.save(existingProduct);
        } else {
            logger.warn("Intento de actualización fallido: Producto con ID {} no encontrado.", id);
            return null; // O lanza una excepción
        }
    }

    // Método optimizado para eliminar productos por ID de usuario
    public void deleteByUsuarioId(Integer usuarioId) {
        logger.info("Eliminando productos asociados al usuario ID: {}", usuarioId);
        try {
            productRepository.deleteByUsuarioId(usuarioId);
        } catch (Exception e) {
            logger.error("Error al eliminar productos del usuario ID: {}", usuarioId, e);
            throw e; // Re-lanza para que el controlador lo maneje
        }
    }

    public void deleteProduct(Long id) {
        logger.info("Eliminando producto con ID: {}", id);
        productRepository.deleteById(id);
    }
}