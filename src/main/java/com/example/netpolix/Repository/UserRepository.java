package com.example.netpolix.Repository;

import com.example.netpolix.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNombreUsuario(String nombreUsuario);
    Usuario findByEmail(String email); // Nuevo método
}
