package com.example.netpolix.Repository;

import com.example.netpolix.model.Notificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionesRepository extends JpaRepository<Notificaciones, Long> {
    List<Notificaciones> findByIdUsuario(Long idUsuario);
    List<Notificaciones> findByMensaje(String mensaje);
}
