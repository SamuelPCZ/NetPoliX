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
@Table(name = "coleccion_video")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoColeccion {

    @Column(name = "coleccion_isan")
    private int isanColeccion;
    
    @Column(name = "video_isan")
    private int isanVideo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
}
