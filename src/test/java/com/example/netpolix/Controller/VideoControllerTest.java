package com.example.netpolix.Controller;

import com.example.netpolix.Controller.VideoController;
import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.Services.NotificacionesService;
import com.example.netpolix.Services.VideoLog;
import com.example.netpolix.model.Video;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.ui.ConcurrentModel;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VideoControllerTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private NotificacionesService notificacionesService;

    @Mock
    private VideoLog videoLog;

    @InjectMocks
    private VideoController videoController;

    public VideoControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSubirVideoABD() throws IOException {
        Model model = new ConcurrentModel();
        when(videoLog.ValidarTitulo("Test Video")).thenReturn(true);
        when(videoLog.ValidarAñoProduccion(any(LocalDate.class))).thenReturn(true);
        when(videoLog.ValidarDuracionVideo("120")).thenReturn(true);
        when(videoLog.PersonasInvolucradas("Director")).thenReturn(true);
        when(videoLog.PersonasInvolucradas("Actor")).thenReturn(true);
        when(videoLog.PersonasInvolucradas("Producer")).thenReturn(true);

        String viewName = videoController.SubirVideoABD("Test Video", LocalDate.of(2022, 1, 1), 10.0, "120", "Action", "English", "Director", "Actor", "Producer", "PG-13", null, 5, model);
        assertEquals("plantillas/SubirVideo", viewName);
        verify(videoRepository, times(1)).save(any(Video.class));
        verify(notificacionesService, times(1)).sendNotificationToAllUsers(anyString(), eq("video"));
    }
}
