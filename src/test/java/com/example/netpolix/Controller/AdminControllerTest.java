package com.example.netpolix.Controller;

import com.example.netpolix.Controller.AdminController;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ConcurrentModel;

import static org.junit.jupiter.api.Assertions.*;

public class AdminControllerTest {

    private final AdminController adminController = new AdminController();

    @Test
    public void testCrearSerie() {
        String viewName = adminController.CrearSerie();
        assertEquals("plantillas/CrearSerie", viewName);
    }

    @Test
    public void testAgregarTemporada() {
        String viewName = adminController.AgregarTemporada();
        assertEquals("/plantillas/AgregarTemporada", viewName);
    }

    @Test
    public void testPaginaVideo() {
        Model model = new ConcurrentModel();
        String viewName = adminController.PaginaVideo(model);
        assertEquals("plantillas/SubirVideo", viewName);
        assertNotNull(model.getAttribute("fechaRegistro"));
    }

    @Test
    public void testGetMethodName() {
        String viewName = adminController.getMethodName();
        assertEquals("plantillas/Admin", viewName);
    }
}
