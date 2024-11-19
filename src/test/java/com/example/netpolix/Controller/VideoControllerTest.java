package com.example.netpolix.Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.Services.NotificacionesService;
import com.example.netpolix.Services.VideoLog;
import com.example.netpolix.model.Video;

public class VideoControllerTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private NotificacionesService notificacionesService;

    @Mock
    private VideoLog videoLog;

    @Mock
    private Model model;

    @InjectMocks
    private VideoController videoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSubirVideoABD() throws IOException {
        String titulo = "Test Video";
        LocalDate año = LocalDate.of(2022, 1, 1);
        double precio = 10.0;
        String duracion = "120";
        String categorias = "Action";
        String idiomaOriginal = "English";
        String directoresString = "Director1";
        String actoresString = "Actor1";
        String productoresString = "Producer1";
        String clasificacion = "PG-13";
        Integer idTemporada = null;
        int calificacion = 5;

        when(videoLog.ValidarTitulo(titulo)).thenReturn(true);
        when(videoLog.ValidarAñoProduccion(año)).thenReturn(true);
        when(videoLog.ValidarDuracionVideo(duracion)).thenReturn(true);
        when(videoLog.PersonasInvolucradas(directoresString)).thenReturn(true);
        when(videoLog.PersonasInvolucradas(actoresString)).thenReturn(true);
        when(videoLog.PersonasInvolucradas(productoresString)).thenReturn(true);

        String result = videoController.SubirVideoABD(titulo, año, precio, duracion, categorias, idiomaOriginal, directoresString, actoresString, productoresString, clasificacion, idTemporada, calificacion, model);

        assertNotNull(result);
        verify(videoRepository).save(any(Video.class));
        verify(notificacionesService).sendNotificationToAllUsers("Nuevo video subido: " + titulo, "video");
    }

    @Test
    public void testValidarIsan() {
        int isan = 123;
        Video video = new Video();
        video.setIsan(isan);
        video.setTitulo("Test Video");

        when(videoRepository.findByIsan(isan)).thenReturn(video);

        Map<String, Object> response = videoController.validarIsan(isan).getBody();

        assertNotNull(response);
        assertTrue((Boolean) response.get("exists"));
        assertEquals("Test Video", response.get("nombre"));
    }
}
