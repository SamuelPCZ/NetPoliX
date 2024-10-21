package com.example.netpolix.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.netpolix.Repository.SerieRepository;
import com.example.netpolix.Services.VideoLog;
import com.example.netpolix.model.Serie;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SerieController {

    private Serie serie = new Serie();
    private VideoLog titulo = new VideoLog();

    @Autowired
    private SerieRepository serieRepository;

    @PostMapping("/CrearSerie")
    public String postMethodName(
        @RequestParam("nombreSerie") String nombre,
        Model model) {

        if(!titulo.ValidarTitulo(nombre)) {
            model.addAttribute("error", "Error: El título no puede tener caracteres especiales");
        } else {
            serie.setNombre(nombre);
            serieRepository.save(serie);
            model.addAttribute("tareaFinalizada", "Serie creada con éxito, el id es: " + serie.getIdSerie());
        }
        return "plantillas/CrearSerie";
    }
    

}
