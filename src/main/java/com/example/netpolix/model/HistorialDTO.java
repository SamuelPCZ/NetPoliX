package com.example.netpolix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialDTO {
    private String nombreVideo;
    private double precio;
    private Historial historial;
    private boolean yaCalificado;
    private int isan;
    private int idUsuario;
}
