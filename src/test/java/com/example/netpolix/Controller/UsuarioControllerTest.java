package com.example.netpolix.Controller;

import com.example.netpolix.Controller.UsuarioController;
import com.example.netpolix.Repository.HistorialRepository;
import com.example.netpolix.Repository.InventarioRepository;
import com.example.netpolix.Repository.UserRepository;
import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.model.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.ui.ConcurrentModel;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private HistorialRepository historialRepository;

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private Principal principal;

    @InjectMocks
    private UsuarioController usuarioController;

    public UsuarioControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowUsuarioPrincipal() {
        Model model = new ConcurrentModel();
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombreUsuario("Test User");

        when(principal.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(usuario);

        String viewName = usuarioController.showUsuarioPrincipal(model, principal);
        assertEquals("plantillas/UsuarioPrincipal", viewName);
        assertEquals(1L, model.getAttribute("usuarioId"));
        assertEquals("Test User", model.getAttribute("usuarioNombre"));
    }

    @Test
    public void testShowReferidosPuntos() {
        Model model = new ConcurrentModel();
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setPuntos(100);

        when(principal.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(usuario);

        String viewName = usuarioController.showReferidosPuntos(model, principal);
        assertEquals("plantillas/ReferidosPuntos", viewName);
        assertEquals(1L, model.getAttribute("usuarioId"));
        assertEquals(100, model.getAttribute("usuarioPuntos"));
    }

    @Test
    public void testIngresarSaldo() {
        Model model = new ConcurrentModel();
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setSaldo(100.0);

        when(principal.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(usuario);

        String viewName = usuarioController.ingresarSaldo(50.0, principal, model);
        assertEquals("redirect:/consultarSaldo", viewName);
        assertEquals(150.0, usuario.getSaldo());
        verify(userRepository, times(1)).save(usuario);
    }
}
