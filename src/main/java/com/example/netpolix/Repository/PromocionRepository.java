package com.example.netpolix.Repository;

import com.example.netpolix.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    List<Promocion> findByFechaInicioBeforeAndFechaFinAfter(LocalDateTime now1, LocalDateTime now2);
}
