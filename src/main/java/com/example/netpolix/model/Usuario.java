package com.example.netpolix.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "contraseña", nullable = false)
    private String contraseña;

    @Column(name = "fecha_registro", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaRegistro;

    @Column(name = "referido_por")
    private Long referidoPor;

    @Column(name = "saldo", columnDefinition = "REAL DEFAULT 0")
    private Double saldo;

    @Column(name = "puntos", columnDefinition = "INTEGER DEFAULT 0")
    private Integer puntos;

    @Column(name = "rol", nullable = false)
    private String rol;
}