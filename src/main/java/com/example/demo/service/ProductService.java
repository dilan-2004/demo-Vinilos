package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import java.util.List;

import jakarta.transaction.Transactional;

@Service
@Transactional
@SuppressWarnings("null")
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteByUsuarioId(Integer usuarioId) {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            if (product.getUsuario() != null && product.getUsuario().getId().equals(usuarioId)) {
                productRepository.deleteById(product.getId());
            }
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
