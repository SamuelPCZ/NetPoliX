package com.example.netpolix.model;

public class ColeccionVideoDTO {
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

    // Getters y setters
    public String getColeccionTitulo() {
        return coleccionTitulo;
    }

    public void setColeccionTitulo(String coleccionTitulo) {
        this.coleccionTitulo = coleccionTitulo;
    }

    public String getVideoTitulo() {
        return videoTitulo;
    }

    public void setVideoTitulo(String videoTitulo) {
        this.videoTitulo = videoTitulo;
    }

    public int getColeccionIsan() {
        return coleccionIsan;
    }

    public void setColeccionIsan(int coleccionIsan) {
        this.coleccionIsan = coleccionIsan;
    }

    public int getVideoIsan() {
        return videoIsan;
    }

    public void setVideoIsan(int videoIsan) {
        this.videoIsan = videoIsan;
    }

}
