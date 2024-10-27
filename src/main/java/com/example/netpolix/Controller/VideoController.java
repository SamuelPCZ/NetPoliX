package com.example.netpolix.Controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.Services.VideoLog;
import com.example.netpolix.model.Video;

@Controller
public class VideoController {
    @Autowired
    private VideoLog video = new VideoLog();

    @Autowired
    private VideoRepository videoRepository;

    @PostMapping("/SubirVideo")
    @Transactional
    public String SubirVideoABD(
            @RequestParam("titulo") String titulo,
            @RequestParam("añoProduccion") LocalDate año,
            @RequestParam("precio") double precio,
            @RequestParam("duracion") String duracion,
            @RequestParam("categorias") String categorias,
            @RequestParam("Lenguaje") String idiomaOriginal,
            @RequestParam("directoresString") String directoresString,
            @RequestParam("actoresString") String actoresString,
            @RequestParam("productoresString") String productoresString,
            @RequestParam("clasificacion") String clasificacion,
            @RequestParam(value = "temporadaId", required = false) Integer idTemporada,
            @RequestParam("calificacion") int calificacion,
            Model model) throws IOException {

        if (!video.ValidarTitulo(titulo)) {
            model.addAttribute("errorMessage", "El título contiene caracteres especiales no permitidos.");
            return "plantillas/SubirVideo";
        }

        if (!video.ValidarAñoProduccion(año)) {
            model.addAttribute("errorMessage", "La fecha de producción no puede ser hoy o en el futuro.");
            return "plantillas/SubirVideo";
        }

        if (!video.ValidarDuracionVideo(duracion)) {
            model.addAttribute("errorMessage", "La duración debe ser mayor a 0 minutos y menor o igual a 240 minutos.");
            return "plantillas/SubirVideo";
        }

        if (!video.PersonasInvolucradas(directoresString) || !video.PersonasInvolucradas(actoresString)
                || !video.PersonasInvolucradas(productoresString)) {
            model.addAttribute("errorMessage", "Debe agregar al menos una persona (director, actor o productor).");
            return "plantillas/SubirVideo";
        }

        Video video = new Video();
        video.setTitulo(titulo);
        video.setAñoProduccion(año);
        video.setPrecio(precio);
        video.setDuracionVideo(duracion);
        video.setCategorias(categorias);
        video.setIdiomaOriginal(idiomaOriginal);
        video.setDirectores(directoresString);
        video.setActores(actoresString);
        video.setProductores(productoresString);
        video.setClasificacion(clasificacion);
        video.setIdTemporada(idTemporada != null ? idTemporada : 0); // Handle null value
        video.setCalificacion(calificacion);

        videoRepository.save(video);

        model.addAttribute("tareaFinalizada", "El video se ha subido correctamente.");
        return "plantillas/SubirVideo";
    }

}
