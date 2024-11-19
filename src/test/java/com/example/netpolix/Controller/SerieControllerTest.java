package com.example.netpolix.Controller;

import com.example.netpolix.Controller.SerieController;
import com.example.netpolix.Repository.SerieRepository;
import com.example.netpolix.Repository.TemporadaRepository;
import com.example.netpolix.model.Serie;
import com.example.netpolix.model.Temporada;
import com.example.netpolix.Services.VideoLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SerieControllerTest {

    @Mock
    private SerieRepository serieRepository;

    @Mock
    private TemporadaRepository temporadaRepository;

    @Mock
    private VideoLog videoLog;

    @Mock
    private Model model;

    @InjectMocks
    private SerieController serieController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostMethodName_ValidTitle() {
        String nombre = "Valid Title";
        short temporadas = 3;

        when(videoLog.ValidarTitulo(nombre)).thenReturn(true);

        String result = serieController.postMethodName(nombre, temporadas, model);

        verify(serieRepository).save(any(Serie.class));
        verify(temporadaRepository, times(3)).save(any(Temporada.class));
        verify(model).addAttribute(eq("tareaFinalizada"), anyString());
    }

    @Test
    public void testPostMethodName_InvalidTitle() {
        String nombre = "Invalid@Title";
        short temporadas = 3;

        when(videoLog.ValidarTitulo(nombre)).thenReturn(false);

        String result = serieController.postMethodName(nombre, temporadas, model);

        verify(model).addAttribute(eq("error"), eq("Error: El título no puede tener caracteres especiales"));
    }
}
