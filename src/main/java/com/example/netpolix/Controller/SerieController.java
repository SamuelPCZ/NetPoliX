package com.example.netpolix.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.example.netpolix.Repository.SerieRepository;
import com.example.netpolix.Repository.TemporadaRepository;
import com.example.netpolix.Services.VideoLog;
import com.example.netpolix.model.Serie;
import com.example.netpolix.model.Temporada;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SerieController {

    private Serie serie = new Serie();
    private VideoLog titulo = new VideoLog();

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private TemporadaRepository temporadaRepository;

    @PostMapping("/CrearSerie")
    @Transactional
    public String postMethodName(
            @RequestParam("nombreSerie") String nombre,
            @RequestParam("temporadas") short temporadas,
            Model model) {

        if(!titulo.ValidarTitulo(nombre)) {
            model.addAttribute("error", "Error: El título no puede tener caracteres especiales");
        } else {
            serie.setNombre(nombre);
            serie.setNumeroTemporadas(temporadas);
            serieRepository.save(serie);

            for (int i = 1; i <= temporadas; i++) {
                Temporada nuevaTemp = new Temporada();
                nuevaTemp.setIdSerie(serie.getIdSerie());
                nuevaTemp.setNumeroTemporada(i);
                nuevaTemp.setNombreSerie(nombre); // Set the new field
                temporadaRepository.save(nuevaTemp);
            }

            model.addAttribute("tareaFinalizada", "Serie creada con éxito, el id es: " + serie.getIdSerie());
        }
        return "plantillas/CrearSerie";
    }
}