package com.example.netpolix.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.netpolix.model.Coleccion;
import com.example.netpolix.model.ColeccionVideoDTO;

import java.util.List;


public interface ColeccionRepository extends JpaRepository<Coleccion, Integer> {

    Coleccion findByIsan(int isan);

}
