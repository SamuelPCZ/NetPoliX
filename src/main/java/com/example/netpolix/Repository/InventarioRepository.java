package com.example.netpolix.Repository;

import com.example.netpolix.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    List<Inventario> findByIdUsuario(Long idUsuario);
    Inventario findByIdUsuarioAndIsan(Long idUsuario, Integer isan); // New method
}
