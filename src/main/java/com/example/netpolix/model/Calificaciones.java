package com.example.netpolix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "calificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Calificaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion")
    private Integer idCalificacion;

    @Column(name = "isan")
    private Integer isan;

    @Column(name = "valor_calificacion")
    private float calificacion;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "fecha_calificacion")
    private LocalDateTime fechaCalificacion = LocalDateTime.now();
}
