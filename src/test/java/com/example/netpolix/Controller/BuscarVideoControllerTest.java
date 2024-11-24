package com.example.netpolix.Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.netpolix.Repository.CarritoItemsRepository;
import com.example.netpolix.Repository.HistorialRepository;
import com.example.netpolix.Repository.UserRepository;
import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.Services.CalificarVideo;
import com.example.netpolix.model.Usuario;
import com.example.netpolix.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class BuscarVideoControllerTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private CalificarVideo calificarVideo;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private BuscarVideoController buscarVideoController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarritoItemsRepository carritoItemsRepository;

    @Mock
    private HistorialRepository historialRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarVideos() {
        String query = "test";
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(userRepository.findByEmail("test@example.com")).thenReturn(usuario);

        List<Video> videos = new ArrayList<>();
        Video video = new Video();
        video.setTitulo("Test Video");
        videos.add(video);

        when(videoRepository.findByTituloContainingIgnoreCase(query)).thenReturn(videos);

        String viewName = buscarVideoController.buscarVideos(query, model, principal);

        verify(model).addAttribute(eq("videos"), anyList());
    }

    @Test
    public void testCalificarVideo() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("test@example.com");

        doNothing().when(calificarVideo).calificarVideo(anyInt(), anyInt(), anyFloat());

        String result = buscarVideoController.calificarVideo(123, 1, 5, "test", redirectAttributes);

        assertEquals("redirect:/buscarVideos?query=test", result);
        verify(redirectAttributes).addFlashAttribute(eq("Mensaje"), anyString());
    }

    @Test
    public void testBuscarVideosCategoria() {
        List<String> categorias = new ArrayList<>();
        categorias.add("Action");

        List<Video> videos = new ArrayList<>();
        Video video = new Video();
        video.setTitulo("Action Movie");
        videos.add(video);

        when(videoRepository.findByCategoria("Action")).thenReturn(videos);

        String viewName = buscarVideoController.buscarVideosCategoria(categorias, model);

        assertEquals("plantillas/resultadosBusqueda", viewName);
        verify(model).addAttribute(eq("videos"), anyList());
    }

    @Test
    public void testBuscarVideosIdioma() {
        String idioma = "English";

        List<Video> videos = new ArrayList<>();
        Video video = new Video();
        video.setTitulo("English Movie");
        videos.add(video);

        when(videoRepository.findByIdiomaOriginal(idioma)).thenReturn(videos);

        String viewName = buscarVideoController.getMethodName(idioma, model);

        assertEquals("plantillas/resultadosBusqueda", viewName);
        verify(model).addAttribute(eq("videos"), anyList());
    }
}