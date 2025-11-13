package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombreProducto", length = 100, nullable = false)
    private String nombre;
    @Column(name = "precioProducto", nullable = false)
    private Integer precio;
    @Column(name = "autorProducto", length = 100, nullable = false)
    private String autor;
    @Column(name = "descripcionProducto", nullable = true)
    private String descripcion;
    @Column(name = "imgProducto", nullable = true)
    private String img;
    @ManyToOne
    @JoinColumn(name = "codigo_usuario")
    private Usuario usuario;
}
