package com.example.netpolix.Controller;

import com.example.netpolix.Controller.CarritoController;
import com.example.netpolix.Repository.*;
import com.example.netpolix.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarritoControllerTest {

    @Mock
    private CarritoItemsRepository carritoItemsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private HistorialRepository historialRepository;

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private PromocionRepository promocionRepository;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @InjectMocks
    private CarritoController carritoController;

    public CarritoControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVerCarrito() {
        when(principal.getName()).thenReturn("test@example.com");
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(userRepository.findByEmail("test@example.com")).thenReturn(usuario);
        when(carritoItemsRepository.findByIdUsuario(1L)).thenReturn(Collections.emptyList());

        String viewName = carritoController.verCarrito(principal, model);
        assertEquals("plantillas/carrito", viewName);
    }

    @Test
    public void testEliminarDelCarrito() {
        doNothing().when(carritoItemsRepository).deleteById(1);

        String viewName = carritoController.eliminarDelCarrito(1);
        assertEquals("redirect:/carrito", viewName);
    }

    @Test
    public void testAgregarAlCarrito() {
        when(principal.getName()).thenReturn("test@example.com");
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(userRepository.findByEmail("test@example.com")).thenReturn(usuario);
        when(carritoItemsRepository.findByIdUsuarioAndIsan(1, 123)).thenReturn(null);

        String viewName = carritoController.agregarAlCarrito(123, principal, model);
        assertEquals("redirect:/buscarVideos", viewName);
    }

    @Test
    public void testRealizarCompra() {
        when(principal.getName()).thenReturn("test@example.com");
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setSaldo(1000.0);
        usuario.setPuntos(0); // Initialize puntos to avoid NullPointerException
        when(userRepository.findByEmail("test@example.com")).thenReturn(usuario);
        when(carritoItemsRepository.findByIdUsuario(1L)).thenReturn(Collections.emptyList());

        String viewName = carritoController.realizarCompra(principal, model);
        assertEquals("redirect:/carrito", viewName);
    }
}
