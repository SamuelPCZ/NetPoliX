package com.example.netpolix.Controller;

import com.example.netpolix.Services.NotificacionesService;
import com.example.netpolix.model.Notificaciones;
import com.example.netpolix.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotificacionesControllerTest {

    @Mock
    private NotificacionesService notificacionesService;

    @Mock
    private Model model;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private NotificacionesController notificacionesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetNotificaciones() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail(email);

        List<Notificaciones> notificacionesList = new ArrayList<>();
        Notificaciones notificacion = new Notificaciones();
        notificacion.setId(1L);
        notificacion.setMensaje("Test Notification");
        notificacionesList.add(notificacion);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(notificacionesService.findUsuarioByEmail(email)).thenReturn(usuario);
        when(notificacionesService.getNotificacionesByUserId(usuario.getId())).thenReturn(notificacionesList);

        String viewName = notificacionesController.getNotificaciones(model);

        verify(model).addAttribute("notificaciones", notificacionesList);
    }
}
