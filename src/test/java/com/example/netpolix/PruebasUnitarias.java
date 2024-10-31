package com.example.netpolix;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.example.netpolix.Services.VideoLog;

import static org.junit.jupiter.api.Assertions.*;

public class PruebasUnitarias {

    @Test
    public void test1(){
        int suma = 1+1;
        assertEquals(2, suma);
    }

    /*
     * Métodos para el validar los campos a la hora de subir un video, ya sea titulo,
     * duración o personas asociadas
     */
    VideoLog video = new VideoLog();
    @Test
    public void testTitulo(){
        assertTrue(video.ValidarTitulo("Sonic el erizo"), "No Deberia dar error por que la cadena no tiene caracteres especiales");
        assertFalse(video.ValidarTitulo("Sonic-el-erizo"), "Deberia dar error por que no acepta caracteres especiales");
    }

    @Test
    public void testAño(){
        LocalDate hoy = LocalDate.now();
        assertFalse(video.ValidarAñoProduccion(hoy), "No deberia dar error, por que la fecha no es futura");
        LocalDate diciembre = LocalDate.of(2024, 12, 1);
        assertFalse(video.ValidarAñoProduccion(diciembre), "La función deberia retornar false por que la fecha es hoy o no ha llegado");

        LocalDate fechaValida = LocalDate.of(2000, 6, 21);
        assertTrue(video.ValidarAñoProduccion(fechaValida), "no deberia dar error por que la fecha es antes de hoy");
    }

    @Test
    public void testDuracionVideo(){
        assertTrue(video.ValidarDuracionVideo("120"), "No deberia dar error, por que lo máximo son 240 minutos");

        assertFalse(video.ValidarDuracionVideo("5000"), "Retorna false, por que lo máximo son 240 minutos");
        assertFalse(video.ValidarDuracionVideo("-50"), "Retorna false, por que es menor que 0 ");
        assertFalse(video.ValidarDuracionVideo("0"), "Retorna false, por que es 0 ");
    }

    @Test
    public void testPersonas(){

        assertTrue(video.PersonasInvolucradas("José, Pedro, Enrique"),"Devuelve true por que no tiene"+ 
        "caracteres especiales, y hay minimo una persona");

        assertFalse(video.PersonasInvolucradas(""), "Retorna false por que no hay ningun elemento");
        assertFalse(video.PersonasInvolucradas("pedro jo$e, M@rio, {+__-}"), "Retorna false por que los nombres son invalidos");
    }

}
