package com.example.netpolix.Services;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class VideoLogTest {

    @InjectMocks
    private VideoLog videoLog;

    public VideoLogTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testValidarTitulo() {
        assertTrue(videoLog.ValidarTitulo("Sonic el erizo"), "No Deberia dar error por que la cadena no tiene caracteres especiales");
        assertFalse(videoLog.ValidarTitulo("Sonic-el-erizo"), "Deberia dar error por que no acepta caracteres especiales");
    }

    @Test
    public void testValidarAñoProduccion() {
        LocalDate hoy = LocalDate.now();
        assertFalse(videoLog.ValidarAñoProduccion(hoy), "No deberia dar error, por que la fecha no es futura");
        LocalDate diciembre = LocalDate.of(2024, 12, 1);
        assertFalse(videoLog.ValidarAñoProduccion(diciembre), "La función deberia retornar false por que la fecha es hoy o no ha llegado");

        LocalDate fechaValida = LocalDate.of(2000, 6, 21);
        assertTrue(videoLog.ValidarAñoProduccion(fechaValida), "no deberia dar error por que la fecha es antes de hoy");
    }

    @Test
    public void testValidarDuracionVideo() {
        assertTrue(videoLog.ValidarDuracionVideo("120"), "No deberia dar error, por que lo máximo son 240 minutos");

        assertFalse(videoLog.ValidarDuracionVideo("5000"), "Retorna false, por que lo máximo son 240 minutos");
        assertFalse(videoLog.ValidarDuracionVideo("-50"), "Retorna false, por que es menor que 0 ");
        assertFalse(videoLog.ValidarDuracionVideo("0"), "Retorna false, por que es 0 ");
    }

    @Test
    public void testPersonasInvolucradas() {
        assertTrue(videoLog.PersonasInvolucradas("José, Pedro, Enrique"), "Devuelve true por que no tiene caracteres especiales, y hay minimo una persona");

        assertFalse(videoLog.PersonasInvolucradas(""), "Retorna false por que no hay ningun elemento");
        assertFalse(videoLog.PersonasInvolucradas("pedro jo$e, M@rio, {+__-}"), "Retorna false por que los nombres son invalidos");
    }
}
