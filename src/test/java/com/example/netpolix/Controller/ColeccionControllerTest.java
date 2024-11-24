package com.example.netpolix.Controller;

import com.example.netpolix.Controller.ColeccionController;
import com.example.netpolix.Repository.*;
import com.example.netpolix.model.Coleccion;
import com.example.netpolix.model.Usuario;
import com.example.netpolix.model.Video;
import com.example.netpolix.model.VideoColeccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ColeccionControllerTest {

    @Mock
    private ColeccionRepository coleccionRepository;

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoColeccionRepository videoColeccionRepository;

    @Mock
    private CarritoItemsRepository carritoItemsRepository;

    @Mock
    private HistorialRepository historialRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @InjectMocks
    private ColeccionController coleccionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearColeccion() {
        String titulo = "Nueva Colección";
        Coleccion coleccion = new Coleccion();
        coleccion.setTitulo(titulo);

        when(coleccionRepository.save(any(Coleccion.class))).thenReturn(coleccion);

        String viewName = coleccionController.crearColeccion(titulo, model);

        verify(coleccionRepository, times(1)).save(any(Coleccion.class));
        verify(model).addAttribute(eq("tareaFinalizada"), anyString());
    }

    @Test
    public void testAgregarVideo() {
        String coleccionIsan = "123";
        String videoIsan = "456";
        Coleccion coleccion = new Coleccion();
        coleccion.setIsan(123);
        Video video = new Video();
        video.setIsan(456);

        when(coleccionRepository.findByIsan(123)).thenReturn(coleccion);
        when(videoRepository.findByIsan(456)).thenReturn(video);

        String viewName = coleccionController.ColeccionYVideo(coleccionIsan, videoIsan, model);

        verify(videoColeccionRepository, times(1)).save(any(VideoColeccion.class));
        verify(model).addAttribute(eq("tareaFinalizada"), anyString());
    }

    @Test
    public void testVerColecciones() {
        List<VideoColeccion> coleccionesYVideos = new ArrayList<>();
        VideoColeccion videoColeccion = new VideoColeccion();
        videoColeccion.setIsanColeccion(123);
        videoColeccion.setIsanVideo(456);
        coleccionesYVideos.add(videoColeccion);

        Coleccion coleccion = new Coleccion();
        coleccion.setIsan(123);
        coleccion.setTitulo("Colección 1");

        Video video = new Video();
        video.setIsan(456);
        video.setTitulo("Video 1");

        when(videoColeccionRepository.findAllByOrderByIsanColeccion()).thenReturn(coleccionesYVideos);
        when(coleccionRepository.findByIsan(123)).thenReturn(coleccion);
        when(videoRepository.findByIsan(456)).thenReturn(video);

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(new Usuario());

        String viewName = coleccionController.verColecciones(model, principal);

        verify(model).addAttribute(eq("coleccionesMap"), anyMap());
    }
}
