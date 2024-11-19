package com.example.netpolix.Controller;

import com.example.netpolix.Repository.SerieRepository;
import com.example.netpolix.Repository.TemporadaRepository;
import com.example.netpolix.model.Serie;
import com.example.netpolix.model.Temporada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TemporadaControllerTest {

    @Mock
    private TemporadaRepository temporadaRepository;

    @Mock
    private SerieRepository serieRepository;

    @Mock
    private Model model;

    @InjectMocks
    private TemporadaController temporadaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPostMethodName_SerieNotFound() {
        int idSerie = 1;
        int numeroTemporada = 1;

        when(serieRepository.findById(idSerie)).thenReturn(Optional.empty());

        String result = temporadaController.postMethodName(idSerie, numeroTemporada, model);

        verify(model).addAttribute(eq("error"), eq("Error: La serie con id: " + idSerie + "No existe"));
    }

    @Test
    public void testPostMethodName_Success() {
        int idSerie = 1;
        int numeroTemporada = 1;
        Serie serie = new Serie();
        serie.setIdSerie(idSerie);
        serie.setNombre("Test Serie");
        serie.setNumeroTemporadas((short) 1);

        when(serieRepository.findById(idSerie)).thenReturn(Optional.of(serie));

        String result = temporadaController.postMethodName(idSerie, numeroTemporada, model);

        verify(temporadaRepository).save(any(Temporada.class));
        verify(serieRepository).save(serie);
        verify(model).addAttribute(eq("tareaExitosa"), eq("Temporada Creada con éxito"));
    }
}
