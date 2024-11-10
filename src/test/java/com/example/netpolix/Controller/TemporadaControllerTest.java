package com.example.netpolix.Controller;

import com.example.netpolix.Controller.TemporadaController;
import com.example.netpolix.Repository.SerieRepository;
import com.example.netpolix.Repository.TemporadaRepository;
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

public class TemporadaControllerTest {

    @Mock
    private TemporadaRepository temporadaRepository;

    @Mock
    private SerieRepository serieRepository;

    @InjectMocks
    private TemporadaController temporadaController;

    public TemporadaControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostMethodName() {
        Model model = new ConcurrentModel();
        Serie serie = new Serie();
        serie.setIdSerie(1);
        serie.setNombre("Test Serie");
        serie.setNumeroTemporadas((short) 1);

        when(serieRepository.findById(1)).thenReturn(java.util.Optional.of(serie));

        String viewName = temporadaController.postMethodName(1, 2, model);
        assertEquals("/plantillas/AgregarTemporada", viewName);
        verify(temporadaRepository, times(1)).save(any(Temporada.class));
        verify(serieRepository, times(1)).save(any(Serie.class));
    }
}
