package com.example.netpolix.model;

import java.util.ArrayList;

public class Coleccion {
    private String tituloColeccion;
    private ArrayList<Video> videosColeccion = new ArrayList<Video>();
    private String descripcion;

    public Coleccion(){

    }

    public Coleccion(String tituloColeccion, ArrayList<Video> videosColeccion, String descripcion) {
        this.tituloColeccion = tituloColeccion;
        this.videosColeccion = videosColeccion;
        this.descripcion = descripcion;
    }

    public String getTituloColeccion() {
        return tituloColeccion;
    }

    public void setTituloColeccion(String tituloColeccion) {
        this.tituloColeccion = tituloColeccion;
    }

    public ArrayList<Video> getVideosColeccion() {
        return videosColeccion;
    }

    public void setVideosColeccion(ArrayList<Video> videosColeccion) {
        this.videosColeccion = videosColeccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    

}
