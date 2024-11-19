package com.example.netpolix.Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.example.netpolix.Repository.*;
import com.example.netpolix.model.*;

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
    private Principal principal;

    @Mock
    private Model model;

    @InjectMocks
    private CarritoController carritoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVerCarrito() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setId(1L);
        List<CarritoItems> carritoItems = new ArrayList<>();
        CarritoItems item = new CarritoItems();
        item.setIsan(123);
        carritoItems.add(item);
        Video video = new Video();
        video.setIsan(123);
        video.setTitulo("Test Video");
        video.setPrecio(10.0);

        when(principal.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(usuario);
        when(carritoItemsRepository.findByIdUsuario(1L)).thenReturn(carritoItems);
        when(videoRepository.findById(123)).thenReturn(Optional.of(video));
        when(promocionRepository.findByFechaInicioBeforeAndFechaFinAfter(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());

        String result = carritoController.verCarrito(principal, model);

        assertNotNull(result);
        verify(model).addAttribute(eq("carritoItems"), anyList());
        verify(model).addAttribute(eq("subtotal"), eq(10.0));
        verify(model).addAttribute(eq("totalValue"), eq("10.00"));
    }

    @Test
    public void testEliminarDelCarrito() {
        Integer idItem = 1;

        String result = carritoController.eliminarDelCarrito(idItem);

        assertNotNull(result);
        verify(carritoItemsRepository).deleteById(idItem);
    }

    @Test
    public void testAgregarAlCarrito() {
        String email = "test@example.com";
        int isan = 123;
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setId(1L);
        CarritoItems item = new CarritoItems();
        item.setIsan(isan);

        when(principal.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(usuario);
        when(carritoItemsRepository.findByIdUsuarioAndIsan(1, isan)).thenReturn(null);

        String result = carritoController.agregarAlCarrito(isan, principal, model);

        assertNotNull(result);
        verify(carritoItemsRepository).save(any(CarritoItems.class));
        verify(model).addAttribute(eq("mensaje"), eq("Video agregado al carrito."));
    }

    @Test
    public void testRealizarCompra() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setId(1L);
        usuario.setSaldo(100.0);
        usuario.setPuntos(0); // Initialize puntos to avoid NullPointerException
        List<CarritoItems> carritoItems = new ArrayList<>();
        CarritoItems item = new CarritoItems();
        item.setIsan(123);
        carritoItems.add(item);
        Video video = new Video();
        video.setIsan(123);
        video.setTitulo("Test Video");
        video.setPrecio(10.0);

        when(principal.getName()).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(usuario);
        when(carritoItemsRepository.findByIdUsuario(1L)).thenReturn(carritoItems);
        when(videoRepository.findById(123)).thenReturn(Optional.of(video));

        String result = carritoController.realizarCompra(principal, model);

        assertNotNull(result);
        verify(userRepository).save(usuario);
        verify(transaccionRepository).save(any(Transaccion.class));
        verify(historialRepository).save(any(Historial.class));
        verify(inventarioRepository).save(any(Inventario.class));
        verify(carritoItemsRepository).deleteAll(carritoItems);
        verify(model).addAttribute(eq("mensaje"), eq("Compra realizada con éxito."));
    }
}
