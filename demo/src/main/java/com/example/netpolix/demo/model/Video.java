package com.example.netpolix.demo.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;


public class Video {
    private String titulo;
    private LocalDate añoProduccion;
    private Duration duracionVideo;
    private byte[] Portada;
    private long ISAN;
    private String[] categorias;
    private String idiomaOriginal;
    private Boolean subTitulos;
    private Boolean doblajes;
    private ArrayList<String> directores = new ArrayList<String>(); 
    private ArrayList<String> actores = new ArrayList<String>(); 
    private ArrayList<String> productores = new ArrayList<String>();
    private String clasificacion;
    private String serie;
    private byte numeroTemporada;
    private short numeroCapitulo;

    public Video(String titulo, LocalDate añoProduccion, Duration duracionVideo, byte[] portada, long iSAN,
            String[] categorias, String idiomaOriginal, Boolean subTitulos, Boolean doblajes,
            ArrayList<String> directores, ArrayList<String> actores, ArrayList<String> productores,
            String clasificacion, String serie, byte numeroTemporada, short numeroCapitulo) {
        this.titulo = titulo;
        this.añoProduccion = añoProduccion;
        this.duracionVideo = duracionVideo;
        Portada = portada;
        ISAN = iSAN;
        this.categorias = categorias;
        this.idiomaOriginal = idiomaOriginal;
        this.subTitulos = subTitulos;
        this.doblajes = doblajes;
        this.directores = directores;
        this.actores = actores;
        this.productores = productores;
        this.clasificacion = clasificacion;
        this.serie = serie;
        this.numeroTemporada = numeroTemporada;
        this.numeroCapitulo = numeroCapitulo;
    }
    
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public LocalDate getAñoProduccion() {
        return añoProduccion;
    }
    public void setAñoProduccion(LocalDate añoProduccion) {
        this.añoProduccion = añoProduccion;
    }
    public Duration getDuracionVideo() {
        return duracionVideo;
    }
    public void setDuracionVideo(Duration duracionVideo) {
        this.duracionVideo = duracionVideo;
    }
    public byte[] getPortada() {
        return Portada;
    }
    public void setPortada(byte[] portada) {
        Portada = portada;
    }
    public long getISAN() {
        return ISAN;
    }
    public void setISAN(long iSAN) {
        ISAN = iSAN;
    }
    public String[] getCategorias() {
        return categorias;
    }
    public void setCategorias(String[] categorias) {
        this.categorias = categorias;
    }
    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }
    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }
    public Boolean getSubTitulos() {
        return subTitulos;
    }
    public void setSubTitulos(Boolean subTitulos) {
        this.subTitulos = subTitulos;
    }
    public Boolean getDoblajes() {
        return doblajes;
    }
    public void setDoblajes(Boolean doblajes) {
        this.doblajes = doblajes;
    }
    public ArrayList<String> getDirectores() {
        return directores;
    }
    public void setDirectores(ArrayList<String> directores) {
        this.directores = directores;
    }
    public ArrayList<String> getActores() {
        return actores;
    }
    public void setActores(ArrayList<String> actores) {
        this.actores = actores;
    }
    public ArrayList<String> getProductores() {
        return productores;
    }
    public void setProductores(ArrayList<String> productores) {
        this.productores = productores;
    }
    public String getClasificacion() {
        return clasificacion;
    }
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
    public String getSerie() {
        return serie;
    }
    public void setSerie(String serie) {
        this.serie = serie;
    }
    public byte getNumeroTemporada() {
        return numeroTemporada;
    }
    public void setNumeroTemporada(byte numeroTemporada) {
        this.numeroTemporada = numeroTemporada;
    }
    public short getNumeroCapitulo() {
        return numeroCapitulo;
    }
    public void setNumeroCapitulo(short numeroCapitulo) {
        this.numeroCapitulo = numeroCapitulo;
    }

    

}
