package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    
}
