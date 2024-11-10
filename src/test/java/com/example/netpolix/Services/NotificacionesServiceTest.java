package com.example.netpolix.Services;

import com.example.netpolix.Services.NotificacionesService;
import com.example.netpolix.Repository.NotificacionesRepository;
import com.example.netpolix.Repository.UserRepository;
import com.example.netpolix.model.Notificaciones;
import com.example.netpolix.model.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificacionesServiceTest {

    @Mock
    private NotificacionesRepository notificacionesRepository;

    @Mock
    private UserRepository usuarioRepository;

    @InjectMocks
    private NotificacionesService notificacionesService;

    public NotificacionesServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendNotificationToAllUsers() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setRol("USER");

        when(usuarioRepository.findAll()).thenReturn(Collections.singletonList(usuario));

        notificacionesService.sendNotificationToAllUsers("Test Message", "info");
        verify(notificacionesRepository, times(1)).save(any(Notificaciones.class));
    }

    @Test
    public void testRemoveNotificationByMessage() {
        Notificaciones notificacion = new Notificaciones();
        notificacion.setMensaje("Test Message");

        when(notificacionesRepository.findByMensaje("Test Message")).thenReturn(Collections.singletonList(notificacion));

        notificacionesService.removeNotificationByMessage("Test Message");
        verify(notificacionesRepository, times(1)).deleteAll(anyList());
    }

    @Test
    public void testGetNotificacionesByUserId() {
        when(notificacionesRepository.findByIdUsuario(1L)).thenReturn(Collections.emptyList());

        List<Notificaciones> notificaciones = notificacionesService.getNotificacionesByUserId(1L);
        assertTrue(notificaciones.isEmpty());
    }

    @Test
    public void testFindUsuarioByEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");

        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(usuario);

        Usuario foundUsuario = notificacionesService.findUsuarioByEmail("test@example.com");
        assertNotNull(foundUsuario);
        assertEquals("test@example.com", foundUsuario.getEmail());
    }

    @Test
    public void testGetAllNotificaciones() {
        when(notificacionesRepository.findAll()).thenReturn(Collections.emptyList());

        List<Notificaciones> notificaciones = notificacionesService.getAllNotificaciones();
        assertTrue(notificaciones.isEmpty());
    }
}
