package com.example.netpolix.Controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCrearSerie() throws Exception {
        mockMvc.perform(get("/CrearSerie"))
                .andExpect(status().isOk())
                .andExpect(view().name("plantillas/CrearSerie"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAgregarTemporada() throws Exception {
        mockMvc.perform(get("/agregarTemporada"))
                .andExpect(status().isOk())
                .andExpect(view().name("/plantillas/AgregarTemporada"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testPaginaVideo() throws Exception {
        mockMvc.perform(get("/SubirVideo"))
                .andExpect(status().isOk())
                .andExpect(view().name("plantillas/SubirVideo"))
                .andExpect(model().attributeExists("fechaRegistro"))
                .andExpect(model().attribute("fechaRegistro", LocalDate.now()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetMethodName() throws Exception {
        mockMvc.perform(get("/Admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("plantillas/Admin"));
    }
}