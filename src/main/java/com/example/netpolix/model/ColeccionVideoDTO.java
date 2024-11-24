package com.example.netpolix.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ColeccionVideoDTO {
    // Getters y setters
    private String coleccionTitulo;
    private String videoTitulo;
    private int coleccionIsan;
    private int videoIsan;

    // Constructor, getters y setters
    public ColeccionVideoDTO(String coleccionTitulo, String videoTitulo, int coleccionIsan, int videoIsan) {
        this.coleccionTitulo = coleccionTitulo;
        this.videoTitulo = videoTitulo;
        this.coleccionIsan = coleccionIsan;
        this.videoIsan = videoIsan;
    }

    public ColeccionVideoDTO(){
        
    }

}
