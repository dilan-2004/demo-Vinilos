// src/main/java/com/example/demo/model/Producto.java
package com.example.demo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private Double precio;
    private String stock;
    private String imagenUrl;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "artista_id")
    private Artista artista;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "genero_id")
    private Genero genero;
}