package com.example.netpolix.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.netpolix.model.Video;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer>{
    boolean existsByIsan(int isan);
    List<Video> findByTituloContainingIgnoreCase(String titulo);
    Video findByIsan(int isan);

    @Query("SELECT v FROM Video v WHERE v.categorias LIKE %:categoria%")
    List<Video> findByCategoria(@Param("categoria") String categoria);

    List<Video> findByIdiomaOriginal(String idiomaOriginal);
}
