package com.example.netpolix.Controller;
import com.example.netpolix.Controller.NotificacionesController;
import com.example.netpolix.Services.NotificacionesService;
import com.example.netpolix.model.Notificaciones;
import com.example.netpolix.model.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.ui.Model;
import org.springframework.ui.ConcurrentModel;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificacionesControllerTest {

    @Mock
    private NotificacionesService notificacionesService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private NotificacionesController notificacionesController;

    public NotificacionesControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetNotificaciones() {
        Model model = new ConcurrentModel();
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContextHolder.setContext(securityContext);
        when(notificacionesService.findUsuarioByEmail("test@example.com")).thenReturn(usuario);
        when(notificacionesService.getNotificacionesByUserId(1L)).thenReturn(Collections.emptyList());

        String viewName = notificacionesController.getNotificaciones(model);
        assertEquals("plantillas/notificaciones", viewName);
        assertTrue(((List<Notificaciones>) model.getAttribute("notificaciones")).isEmpty());
    }
}
