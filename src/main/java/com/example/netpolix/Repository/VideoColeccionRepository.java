package com.example.netpolix.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.netpolix.model.Coleccion;
import com.example.netpolix.model.VideoColeccion;

public interface VideoColeccionRepository extends JpaRepository<VideoColeccion, Integer>{
    
    List<VideoColeccion> findAllByOrderByIsanColeccion();
}
