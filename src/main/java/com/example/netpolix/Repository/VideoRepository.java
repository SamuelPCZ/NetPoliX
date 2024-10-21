package com.example.netpolix.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.netpolix.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer>{
    boolean existsByIsan(int isan);

}
