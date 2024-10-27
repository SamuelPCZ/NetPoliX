package com.example.netpolix.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.model.Video;

@Controller
public class BuscarVideoController {

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping("/BuscarVideo")
    public String showBuscarVideo() {
        return "plantillas/buscarVideos";
    }

    @GetMapping("/buscarVideos")
    @Transactional
    public String buscarVideos(@RequestParam(value = "query", required = false) String query, Model model) {
        if (query == null || query.isEmpty()) {
            model.addAttribute("errorMessage", "El parámetro de búsqueda es obligatorio.");
            return "plantillas/buscarVideos";
        }
        List<Video> videos = videoRepository.findByTituloContainingIgnoreCase(query);
        model.addAttribute("videos", videos);
        return "plantillas/resultadosBusqueda";
    }
}