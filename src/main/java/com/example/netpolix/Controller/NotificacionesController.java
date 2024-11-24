package com.example.netpolix.Controller;

import com.example.netpolix.Services.NotificacionesService;
import com.example.netpolix.model.Notificaciones;
import com.example.netpolix.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NotificacionesController {

    @Autowired
    private NotificacionesService notificacionesService;

    public NotificacionesController(NotificacionesService notificacionesService) {
        this.notificacionesService = notificacionesService;
    }

    @Transactional
    @GetMapping("/notificaciones")
    public String getNotificaciones(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Usuario usuario = notificacionesService.findUsuarioByEmail(email);
        List<Notificaciones> notificaciones = notificacionesService.getNotificacionesByUserId(usuario.getId());
        model.addAttribute("notificaciones", notificaciones);
        return "plantillas/notificaciones";
    }
}