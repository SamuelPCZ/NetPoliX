package com.example.netpolix.Controller;

import com.example.netpolix.Controller.PromocionController;
import com.example.netpolix.Services.NotificacionesService;
import com.example.netpolix.Services.PromocionService;
import com.example.netpolix.model.Promocion;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.ui.ConcurrentModel;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PromocionControllerTest {

    @Mock
    private PromocionService promocionService;

    @Mock
    private NotificacionesService notificacionesService;

    @InjectMocks
    private PromocionController promocionController;

    public PromocionControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearPromocion() {
        Model model = new ConcurrentModel();
        when(promocionService.hasActivePromotions()).thenReturn(false);

        String viewName = promocionController.crearPromocion("Test Promo", 10.0f, "2023-10-01T00:00:00", "2023-10-10T00:00:00", model);
        assertEquals("redirect:/promociones", viewName);
        verify(promocionService, times(1)).createPromotion(anyString(), anyFloat(), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    public void testEliminarPromocion() {
        String viewName = promocionController.eliminarPromocion(1L);
        assertEquals("redirect:/promociones", viewName);
        verify(promocionService, times(1)).deletePromotion(1L);
    }

    @Test
    public void testVerPromociones() {
        Model model = new ConcurrentModel();
        when(promocionService.getActivePromotions()).thenReturn(Collections.emptyList());

        String viewName = promocionController.verPromociones(model);
        assertEquals("plantillas/promociones", viewName);
        assertTrue(((List<Promocion>) model.getAttribute("promociones")).isEmpty());
    }
}
