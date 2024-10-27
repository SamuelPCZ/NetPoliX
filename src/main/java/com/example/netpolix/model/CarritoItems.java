package com.example.netpolix.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "carrito_items")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CarritoItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Integer idItem;

    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(name = "isan")
    private Integer isan;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "fecha_agregado")
    private LocalDateTime fechaAgregado = LocalDateTime.now();

    // Getters and Setters
}
