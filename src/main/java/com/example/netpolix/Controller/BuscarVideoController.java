package com.example.netpolix.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.Services.CalificarVideo;
import com.example.netpolix.model.Video;

@Controller
public class BuscarVideoController {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CalificarVideo video;

    @GetMapping("/BuscarVideo")
    public String showBuscarVideo() {
        return "plantillas/buscarVideos";
    }

    @GetMapping("/buscarVideos")
    @Transactional
    public String buscarVideos(@RequestParam(value = "query", required = false) String query, Model model) {
        List<Video> videos = new ArrayList<>();
        if (query == null || query.isEmpty()) {
            model.addAttribute("errorMessage", "El parámetro de búsqueda es obligatorio.");
            return "plantillas/buscarVideos";
        }
        videos = videoRepository.findByTituloContainingIgnoreCase(query);
        model.addAttribute("videos", videos);
        return "plantillas/resultadosBusqueda";
    }

    @PostMapping("/calificarVideo")
    public String calificarVideo(
            @RequestParam("isan") int isan,
            @RequestParam("calificacion") int calificacion,
            @RequestParam("query") String query,
            RedirectAttributes redirectAttributes) {
        video.calificarVideo(isan, calificacion);
        redirectAttributes.addFlashAttribute("Mensaje", "Calificación enviada con éxito");
        return "redirect:/buscarVideos?query=" + query;

    }

    @GetMapping("/buscarVideosCategoria")
    public String buscarVideosCategoria(@RequestParam(value = "categorias", required = false) List<String> categorias,
            Model model) {
        List<Video> videos = new ArrayList<>();
        if (categorias == null || categorias.isEmpty()) {
            model.addAttribute("errorMessage", "Si vas a buscar por categorías, selecciona mínimo 1.");
            return "plantillas/buscarVideos"; // Retorna a la página de búsqueda
        }

        // Buscar videos por cada categoría seleccionada
        for (String categoria : categorias) {
            List<Video> videosPorCategoria = videoRepository.findByCategoria(categoria);
            videos.addAll(videosPorCategoria); // Agrega los videos encontrados
        }

        // Evitar duplicados si es necesario
        List<Video> uniqueVideos = new ArrayList<>(new HashSet<>(videos));

        // Agregar los videos al modelo
        model.addAttribute("videos", uniqueVideos);
        return "plantillas/resultadosBusqueda"; // Retorna a la página de resultados
    }

    @GetMapping("/buscarVideosIdioma")
    public String getMethodName(@RequestParam(value = "idiomas", required = false) String idioma,
    Model model) {
        List<Video> videos = new ArrayList<>();
        if(idioma.isEmpty()){
            model.addAttribute("errorMessage", "Si vas a buscar por idioma, selecciona al menos uno");
            return "plantillas/buscarVideos";
        }

        videos = videoRepository.findByIdiomaOriginal(idioma);
        model.addAttribute("videos", videos);
        return "plantillas/resultadosBusqueda";
    }
    


}