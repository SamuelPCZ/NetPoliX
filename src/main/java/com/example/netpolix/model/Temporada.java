package com.example.netpolix.model;

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
@Table(name = "Temporadas")
@Data 
@AllArgsConstructor
@NoArgsConstructor
public class Temporada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int idTemporada;

    @Column(name = "id_serie")
    public int idSerie; //llave foranea

    @Column(name = "numero_temporada")
    public int NumeroTemporada;
    
}
