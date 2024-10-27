package com.example.netpolix.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.example.netpolix.Repository.SerieRepository;
import com.example.netpolix.Repository.TemporadaRepository;
import com.example.netpolix.model.Serie;
import com.example.netpolix.model.Temporada;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TemporadaController {

    @Autowired
    private TemporadaRepository temporadaRepository;

    @Autowired
    private SerieRepository serieRepository;

    @PostMapping("/agregarTemporada")
    @Transactional
    public String postMethodName(
            @RequestParam("idSerie") int idSerie,
            @RequestParam("numeroTemporada") int numeroTemporada,
            Model model) {

        Serie serie = serieRepository.findById(idSerie).orElse(null);

        if(serie == null){
            model.addAttribute("error", "Error: La serie con id: " + idSerie + "No existe");
        } else {
            Temporada nuevaTemp = new Temporada();
            nuevaTemp.setIdSerie(idSerie);
            nuevaTemp.setNumeroTemporada(numeroTemporada);
            nuevaTemp.setNombreSerie(serie.getNombre()); // Set the new field

            temporadaRepository.save(nuevaTemp);

            serie.setNumeroTemporadas((short) (serie.getNumeroTemporadas() + 1));
            serieRepository.save(serie);

            model.addAttribute("tareaExitosa", "Temporada Creada con éxito");
        }
        return "/plantillas/AgregarTemporada";
    }
}