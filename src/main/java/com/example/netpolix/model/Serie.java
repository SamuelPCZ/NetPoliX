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
@Table(name = "Series")
@Data 
@AllArgsConstructor
@NoArgsConstructor

public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_serie")
    public int idSerie;

    @Column(name = "nombre_serie")
    public String nombre;

    @Column(name = "numero_temporadas")
    private short numeroTemporadas = 1;
    
    
}
