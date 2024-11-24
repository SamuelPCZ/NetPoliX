package com.example.netpolix.Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.netpolix.Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.netpolix.model.Usuario;
import com.example.netpolix.model.Video;
import com.example.netpolix.model.Historial;
import com.example.netpolix.model.Inventario;

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
    private Model model;

    @Mock
    private Principal principal;

    @Mock
    private CalificacionesRepository calificacionesRepository;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowUsuarioPrincipal() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setId(1L);
        usuario.setNombreUsuario("testUser");

        when(principal.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(usuario);

        String result = usuarioController.showUsuarioPrincipal(model, principal);
        assertNotNull(result);
        verify(model).addAttribute("usuarioId", usuario.getId());
        verify(model).addAttribute("usuarioNombre", usuario.getNombreUsuario());
    }

    @Test
    public void testShowReferidosPuntos() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setId(1L);
        usuario.setPuntos(100);
        when(principal.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(usuario);
        String result = usuarioController.showReferidosPuntos(model, principal);
        assertNotNull(result);
        verify(model).addAttribute("usuarioId", usuario.getId());
        verify(model).addAttribute("usuarioPuntos", usuario.getPuntos());
    }

    @Test
    public void testShowConsultarSaldo() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setId(1L);
        usuario.setSaldo(100.0);
        when(principal.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(usuario);
        String result = usuarioController.showConsultarSaldo(model, principal);
        assertNotNull(result);
        verify(model).addAttribute("usuario", usuario);
    }

    @Test
    public void testIngresarSaldo() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setId(1L);
        usuario.setSaldo(100.0);
        when(principal.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(usuario);
        String result = usuarioController.ingresarSaldo(50.0, principal, model);
        assertNotNull(result);
        assertEquals(150.0, usuario.getSaldo());
        verify(userRepository).save(usuario);
    }

    @Test
    public void testShowHistorialCompras() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setId(1L);
        List<Historial> historialCompras = new ArrayList<>();
        Historial historial = new Historial();
        historial.setIdUsuario(1L);
        historial.setIsan(123);
        historialCompras.add(historial);
        Video video = new Video();
        video.setIsan(123);
        video.setTitulo("Test Video");
        video.setPrecio(10.0);
        when(principal.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(usuario);
        when(historialRepository.findByIdUsuario(1L)).thenReturn(historialCompras);
        when(videoRepository.findById(123)).thenReturn(Optional.of(video));
        String result = usuarioController.showHistorialCompras(model, principal);
        assertNotNull(result);
        verify(model).addAttribute(eq("historialCompras"), anyList());
    }

    @Test
    public void testShowInventarioVideos() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setId(1L);
        List<Inventario> inventarioVideos = new ArrayList<>();
        Inventario inventario = new Inventario();
        inventario.setIdUsuario(1L);
        inventario.setIsan(123);
        inventarioVideos.add(inventario);
        Video video = new Video();
        video.setIsan(123);
        video.setTitulo("Test Video");
        when(principal.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(usuario);
        when(inventarioRepository.findByIdUsuario(1L)).thenReturn(inventarioVideos);
        when(videoRepository.findById(123)).thenReturn(Optional.of(video));
        String result = usuarioController.showInventarioVideos(model, principal);
        assertNotNull(result);
        verify(model).addAttribute(eq("inventarioVideos"), anyList());
    }
}
