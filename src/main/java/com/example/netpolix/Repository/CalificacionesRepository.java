package com.example.netpolix.Repository;

import com.example.netpolix.model.Calificaciones;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalificacionesRepository extends JpaRepository<Calificaciones, Integer> {
    List<Calificaciones> findByIsan(int isan);
    List<Calificaciones> findByIsanAndIdUsuario(int isan, int idUsuario);
    List<Calificaciones> findByIdUsuario(int idUsuario);
}
