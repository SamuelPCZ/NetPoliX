package com.example.netpolix.Controller;

import com.example.netpolix.Controller.SerieController;
import com.example.netpolix.Repository.SerieRepository;
import com.example.netpolix.Repository.TemporadaRepository;
import com.example.netpolix.Services.VideoLog;
import com.example.netpolix.model.Serie;
import com.example.netpolix.model.Temporada;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.ui.ConcurrentModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SerieControllerTest {

    @Mock
    private SerieRepository serieRepository;

    @Mock
    private TemporadaRepository temporadaRepository;

    @Mock
    private VideoLog videoLog;

    @InjectMocks
    private SerieController serieController;

    public SerieControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostMethodName() {
        Model model = new ConcurrentModel();
        when(videoLog.ValidarTitulo("Test Serie")).thenReturn(true);

        String viewName = serieController.postMethodName("Test Serie", (short) 2, model);
        assertEquals("plantillas/CrearSerie", viewName);
        verify(serieRepository, times(1)).save(any(Serie.class));
        verify(temporadaRepository, times(2)).save(any(Temporada.class));
    }
}
