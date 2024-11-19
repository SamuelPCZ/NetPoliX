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

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_compras_alquileres")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer idHistorial;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "isan")
    private Integer isan;

    @Column(name = "id_transaccion")
    private Integer idTransaccion;

    @Column(name = "tipo_transacción")
    private String tipoTransaccion;

    @Column(name = "fecha_transacción")
    private LocalDateTime fechaTransaccion = LocalDateTime.now();
}
