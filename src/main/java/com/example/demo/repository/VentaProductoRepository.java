package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.VentaProducto;

@Repository
public interface VentaProductoRepository extends JpaRepository<VentaProducto, Long> {
    
}
