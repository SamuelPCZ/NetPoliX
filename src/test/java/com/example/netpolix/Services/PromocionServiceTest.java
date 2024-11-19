package com.example.netpolix.Services;

import com.example.netpolix.Services.PromocionService;
import com.example.netpolix.Repository.PromocionRepository;
import com.example.netpolix.model.Promocion;
import com.example.netpolix.Services.NotificacionesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PromocionServiceTest {

    @Mock
    private PromocionRepository promocionRepository;

    @Mock
    private NotificacionesService notificacionesService;

    @InjectMocks
    private PromocionService promocionService;

    public PromocionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetActivePromotions() {
        LocalDateTime now = LocalDateTime.now();
        when(promocionRepository.findByFechaInicioBeforeAndFechaFinAfter(now, now)).thenReturn(Collections.emptyList());

        List<Promocion> promociones = promocionService.getActivePromotions();
        assertTrue(promociones.isEmpty());
    }

    @Test
    public void testCreatePromotion() {
        Promocion promocion = new Promocion();
        promocion.setDescripcion("Test Promotion");
        promocion.setDescuento(10.0f);
        promocion.setFechaInicio(LocalDateTime.now());
        promocion.setFechaFin(LocalDateTime.now().plusDays(1));

        when(promocionRepository.save(any(Promocion.class))).thenReturn(promocion);

        Promocion createdPromocion = promocionService.createPromotion("Test Promotion", 10.0f, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        assertNotNull(createdPromocion);
        assertEquals("Test Promotion", createdPromocion.getDescripcion());
    }

    @Test
    public void testDeletePromotion() {
        Promocion promocion = new Promocion();
        promocion.setId(1L);
        promocion.setDescripcion("Test Promotion");

        when(promocionRepository.findById(1L)).thenReturn(java.util.Optional.of(promocion));

        promocionService.deletePromotion(1L);
        verify(promocionRepository, times(1)).delete(promocion);
    }

    @Test
    public void testHasActivePromotions() {
        when(promocionRepository.findByFechaInicioBeforeAndFechaFinAfter(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(Collections.emptyList());

        boolean hasActivePromotions = promocionService.hasActivePromotions();
        assertFalse(hasActivePromotions);
    }
}
