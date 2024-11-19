package com.example.netpolix.model;

import java.time.LocalDate;

import jakarta.persistence.*;
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
    private Integer idTemporada;

    @Column(name = "id_serie")
    private Integer serieId;

    @Transient
    private String calificacionPromedio;

    @Transient
    private boolean enCarrito;

    @Transient
    private boolean comprado;
}