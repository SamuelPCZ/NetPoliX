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
@Table(name = "Videos")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Video {

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "año_produccion")
    private LocalDate añoProduccion;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro = LocalDate.now();

    @Column(name = "duración")
    private String duracionVideo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "isan")
    private int isan;

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

    @Column(name = "id_serie")
    private int idSerie;

    @Column(name = "id_temporada")
    private byte idTemporada;

    @Column(name = "calificacion")
    private byte calificacion;

    

}
