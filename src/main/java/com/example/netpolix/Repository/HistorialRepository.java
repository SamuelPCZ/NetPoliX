package com.example.netpolix.Repository;

import com.example.netpolix.model.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistorialRepository extends JpaRepository<Historial, Integer> {
    List<Historial> findByIdUsuario(Long idUsuario);
}