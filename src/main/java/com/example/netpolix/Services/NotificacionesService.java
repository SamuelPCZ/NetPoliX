package com.example.netpolix.Services;

import com.example.netpolix.Repository.NotificacionesRepository;
import com.example.netpolix.Repository.UserRepository;
import com.example.netpolix.model.Notificaciones;
import com.example.netpolix.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionesService {
    @Autowired
    private NotificacionesRepository notificacionesRepository;

    @Autowired
    private UserRepository usuarioRepository;

    @Transactional
    public void sendNotificationToAllUsers(String message, String type) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        for (Usuario usuario : usuarios) {
            if ("USER".equals(usuario.getRol())) {
                Notificaciones notificacion = new Notificaciones();
                notificacion.setIdUsuario(usuario.getId());
                notificacion.setMensaje(message);
                notificacion.setTipo(type);
                notificacion.setFechaEnvio(LocalDateTime.now());
                notificacionesRepository.save(notificacion);
            }
        }
    }

    public void removeNotificationByMessage(String message) {
        List<Notificaciones> notificaciones = notificacionesRepository.findByMensaje(message);
        notificacionesRepository.deleteAll(notificaciones);
    }

    public List<Notificaciones> getNotificacionesByUserId(Long userId) {
        return notificacionesRepository.findByIdUsuario(userId);
    }

    public Usuario findUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Notificaciones> getAllNotificaciones() {
        return notificacionesRepository.findAll();
    }
}