package com.example.netpolix.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.netpolix.model.Serie;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Integer>{
    boolean existsById(int idSerie);

}
