package com.example.netpolix.Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.Services.CalificarVideo;
import com.example.netpolix.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBuscarVideos() {
        String query = "Test Video";
        List<Video> videos = new ArrayList<>();
        Video video = new Video();
        video.setTitulo("Test Video");
        videos.add(video);

        when(videoRepository.findByTituloContainingIgnoreCase(query)).thenReturn(videos);

        String result = buscarVideoController.buscarVideos(query, model, null);

        assertNotNull(result);
        verify(model).addAttribute("videos", videos);
    }

    @Test
    public void testCalificarVideo() {
        int isan = 123;
        int calificacion = 5;
        int idUsuario = 1;
        String query = "Test Video";

        doNothing().when(calificarVideo).calificarVideo(isan, idUsuario, calificacion);

        String result = buscarVideoController.calificarVideo(isan, idUsuario, calificacion, query, redirectAttributes);

        assertNotNull(result);
        verify(calificarVideo).calificarVideo(isan, idUsuario, calificacion);
        verify(redirectAttributes).addFlashAttribute("Mensaje", "Calificación enviada con éxito");
    }

    @Test
    public void testBuscarVideosCategoria() {
        List<String> categorias = new ArrayList<>();
        categorias.add("Acción");
        List<Video> videos = new ArrayList<>();
        Video video = new Video();
        video.setTitulo("Test Video");
        videos.add(video);

        when(videoRepository.findByCategoria("Acción")).thenReturn(videos);

        String result = buscarVideoController.buscarVideosCategoria(categorias, model);

        assertNotNull(result);
        verify(model).addAttribute("videos", videos);
    }

    @Test
    public void testBuscarVideosIdioma() {
        String idioma = "Español";
        List<Video> videos = new ArrayList<>();
        Video video = new Video();
        video.setTitulo("Test Video");
        videos.add(video);

        when(videoRepository.findByIdiomaOriginal(idioma)).thenReturn(videos);

        String result = buscarVideoController.getMethodName(idioma, model);

        assertNotNull(result);
        verify(model).addAttribute("videos", videos);
    }
}
