package com.example.netpolix.Controller;

import com.example.netpolix.Repository.CarritoItemsRepository;
import com.example.netpolix.Repository.HistorialRepository;
import com.example.netpolix.Repository.UserRepository;
import com.example.netpolix.model.CarritoItems;
import com.example.netpolix.model.Usuario;
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

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class BuscarVideoController {

    private final VideoRepository videoRepository;

    private final CalificarVideo calificarVideo;

    private final CarritoItemsRepository carritoItemsRepository;

    private final UserRepository userRepository;

    private final HistorialRepository historialRepository;

    public BuscarVideoController(VideoRepository videoRepository, CalificarVideo calificarVideo, CarritoItemsRepository carritoItemsRepository, UserRepository userRepository, HistorialRepository historialRepository) {
        this.videoRepository = videoRepository;
        this.calificarVideo = calificarVideo;
        this.carritoItemsRepository = carritoItemsRepository;
        this.userRepository = userRepository;
        this.historialRepository = historialRepository;
    }

    @GetMapping("/BuscarVideo")
    public String showBuscarVideo() {
        return "plantillas/buscarVideos";
    }

    @GetMapping("/buscarVideos")
    @Transactional
    public String buscarVideos(@RequestParam(value = "query", required = false) String query, Model model, Principal principal) {
        List<Video> videos = new ArrayList<>();
        if (query == null || query.isEmpty()) {
            model.addAttribute("errorMessage", "El parámetro de búsqueda es obligatorio.");
            return "plantillas/buscarVideos";
        }
        videos = videoRepository.findByTituloContainingIgnoreCase(query);
        Usuario usuario = userRepository.findByEmail(principal.getName());
        List<CarritoItems> carritoItems = carritoItemsRepository.findByIdUsuario(usuario.getId());
        Set<Integer> isanEnCarrito = carritoItems.stream().map(CarritoItems::getIsan).collect(Collectors.toSet());

        for (Video video : videos) {
            String calificacionPromedio = calificarVideo.obtenerCalificacionPromedio(video.getIsan());
            video.setCalificacionPromedio(calificacionPromedio);
            video.setEnCarrito(isanEnCarrito.contains(video.getIsan()));
            boolean videoComprado = historialRepository.existsByIdUsuarioAndIsan(usuario.getId(), video.getIsan());
            video.setComprado(videoComprado); // Añadir esta línea
        }
        model.addAttribute("videos", videos);
        return "plantillas/resultadosBusqueda";
    }

    @PostMapping("/calificarVideo")
    public String calificarVideo(
            @RequestParam("isan") int isan,
            @RequestParam("idUsuario") int idUsuario,
            @RequestParam("calificacion") float calificacion,
            @RequestParam("query") String query,
            RedirectAttributes redirectAttributes) {
        try {
            calificarVideo.calificarVideo(isan, idUsuario, calificacion);
            redirectAttributes.addFlashAttribute("Mensaje", "Calificación enviada con éxito");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("Mensaje", e.getMessage());
        }
        return "redirect:/buscarVideos?query=" + query;
    }

    @GetMapping("/buscarVideosCategoria")
    public String buscarVideosCategoria(@RequestParam(value = "categorias", required = false) List<String> categorias,
                                        Model model) {
        List<Video> videos = new ArrayList<>();
        if (categorias == null || categorias.isEmpty()) {
            model.addAttribute("errorMessage", "Si vas a buscar por categorías, selecciona mínimo 1.");
            return "plantillas/buscarVideos";
        }

        for (String categoria : categorias) {
            List<Video> videosPorCategoria = videoRepository.findByCategoria(categoria);
            videos.addAll(videosPorCategoria);
        }

        List<Video> uniqueVideos = new ArrayList<>(new HashSet<>(videos));
        model.addAttribute("videos", uniqueVideos);
        return "plantillas/resultadosBusqueda";
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