package com.example.netpolix.Controller;

import com.example.netpolix.Controller.BrowseHomePage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BrowseHomePageTest {

    private final BrowseHomePage browseHomePage = new BrowseHomePage();

    @Test
    public void testHome() {
        String viewName = browseHomePage.Home();
        assertEquals("plantillas/home", viewName);
    }

    @Test
    public void testIniciarSesion() {
        String viewName = browseHomePage.IniciarSesion();
        assertEquals("plantillas/InicioSesion", viewName);
    }

    @Test
    public void testRegistrarse() {
        String viewName = browseHomePage.Registrarse();
        assertEquals("plantillas/Registrarse", viewName);
    }
}
