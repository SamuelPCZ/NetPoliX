package com.example.netpolix.Controller;

import com.example.netpolix.Controller.BuscarVideoController;
import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.Services.CalificarVideo;
import com.example.netpolix.model.Video;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BuscarVideoControllerTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private CalificarVideo calificarVideo;

    @InjectMocks
    private BuscarVideoController buscarVideoController;

    public BuscarVideoControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowBuscarVideo() {
        String viewName = buscarVideoController.showBuscarVideo();
        assertEquals("plantillas/buscarVideos", viewName);
    }

    @Test
    public void testBuscarVideos() {
        Model model = new ConcurrentModel();
        when(videoRepository.findByTituloContainingIgnoreCase("test")).thenReturn(Collections.emptyList());

        String viewName = buscarVideoController.buscarVideos("test", model);
        assertEquals("plantillas/resultadosBusqueda", viewName);
        assertTrue(((List<Video>) model.getAttribute("videos")).isEmpty());
    }

    @Test
    public void testCalificarVideo() {
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        doNothing().when(calificarVideo).calificarVideo(anyInt(), anyInt());

        String viewName = buscarVideoController.calificarVideo(123, 5, "test", redirectAttributes);
        assertEquals("redirect:/buscarVideos?query=test", viewName);
        verify(calificarVideo, times(1)).calificarVideo(123, 5);
    }

    @Test
    public void testBuscarVideosCategoria() {
        Model model = new ConcurrentModel();
        when(videoRepository.findByCategoria("test")).thenReturn(Collections.emptyList());

        String viewName = buscarVideoController.buscarVideosCategoria(Collections.singletonList("test"), model);
        assertEquals("plantillas/resultadosBusqueda", viewName);
        assertTrue(((List<Video>) model.getAttribute("videos")).isEmpty());
    }

    @Test
    public void testBuscarVideosIdioma() {
        Model model = new ConcurrentModel();
        when(videoRepository.findByIdiomaOriginal("es")).thenReturn(Collections.emptyList());

        String viewName = buscarVideoController.getMethodName("es", model);
        assertEquals("plantillas/resultadosBusqueda", viewName);
        assertTrue(((List<Video>) model.getAttribute("videos")).isEmpty());
    }
}
