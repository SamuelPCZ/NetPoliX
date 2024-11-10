package com.example.netpolix.Repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

class SerieRepositoryTest {

    @Mock
    private SerieRepository serieRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExistsById_ShouldReturnTrue_WhenSerieExists() {
        int idSerie = 1;

        // Simulación del comportamiento de existsById para el ID 1
        when(serieRepository.existsById(idSerie)).thenReturn(true);

        // Verificación del resultado
        assertTrue(serieRepository.existsById(idSerie), "Expected existsById to return true");
    }

    @Test
    void testExistsById_ShouldReturnFalse_WhenSerieDoesNotExist() {
        int idSerie = 2;

        // Simulación del comportamiento de existsById para un ID que no existe
        when(serieRepository.existsById(idSerie)).thenReturn(false);

        // Verificación del resultado
        assertFalse(serieRepository.existsById(idSerie), "Expected existsById to return false");
    }
}

