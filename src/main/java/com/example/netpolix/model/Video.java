package com.example.netpolix.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "videos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "isan")
    private int isan;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "año_producción")
    private LocalDate añoProduccion;

    @Column(name = "precio")
    private double precio;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro = LocalDate.now();

    @Column(name = "duración")
    private String duracionVideo;

    @Column(name = "Categorias")
    private String categorias;

    @Column(name = "idioma_original")
    private String idiomaOriginal;

    @Column(name = "directores")
    private String directores;

    @Column(name = "actores")
    private String actores;

    @Column(name = "productores")
    private String productores;

    @Column(name = "clasificación")
    private String clasificacion;

    @Column(name = "id_temporada")
    private Integer idTemporada; // Change to Integer to allow null values

    @Column(name = "calificacion")
    private double calificacion;
}