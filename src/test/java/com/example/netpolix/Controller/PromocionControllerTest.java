package com.example.netpolix.Controller;

import com.example.netpolix.Controller.PromocionController;
import com.example.netpolix.Services.NotificacionesService;
import com.example.netpolix.Services.PromocionService;
import com.example.netpolix.model.Promocion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PromocionControllerTest {

    @Mock
    private PromocionService promocionService;

    @Mock
    private NotificacionesService notificacionesService;

    @Mock
    private Model model;

    @InjectMocks
    private PromocionController promocionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearPromocion_ActivePromotionsExist() {
        when(promocionService.hasActivePromotions()).thenReturn(true);

        String result = promocionController.crearPromocion("Test Promo", 10.0f, "2023-10-01T00:00:00", "2023-10-31T23:59:59", model);

        verify(model).addAttribute("errorMessage", "Ya hay promociones activas. No se pueden crear más promociones.");
    }

    @Test
    public void testCrearPromocion_Success() {
        when(promocionService.hasActivePromotions()).thenReturn(false);

        String result = promocionController.crearPromocion("Test Promo", 10.0f, "2023-10-01T00:00:00", "2023-10-31T23:59:59", model);

        verify(promocionService).createPromotion(eq("Test Promo"), eq(10.0f), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    public void testEliminarPromocion() {
        Long idPromocion = 1L;

        String result = promocionController.eliminarPromocion(idPromocion);

        verify(promocionService).deletePromotion(idPromocion);
    }

    @Test
    public void testVerPromociones() {
        List<Promocion> promociones = Collections.singletonList(new Promocion());
        when(promocionService.getActivePromotions()).thenReturn(promociones);

        String result = promocionController.verPromociones(model);

        verify(model).addAttribute("promociones", promociones);
    }
}
