package com.example.netpolix.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.netpolix.model.Temporada;

@Repository
public interface TemporadaRepository extends JpaRepository<Temporada, Integer>{


}
