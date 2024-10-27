package com.example.netpolix.Repository;

import com.example.netpolix.model.CarritoItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarritoItemsRepository extends JpaRepository<CarritoItems, Integer> {
    List<CarritoItems> findByIdUsuario(Long idUsuario);
    CarritoItems findByIdUsuarioAndIsan(Integer idUsuario, Integer isan);
}