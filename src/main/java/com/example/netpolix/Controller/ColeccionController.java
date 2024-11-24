package com.example.netpolix.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.netpolix.Repository.*;
import com.example.netpolix.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ColeccionController {

    private final ColeccionRepository coleccionRepository;
    private final VideoColeccionRepository videoColeccionRepository;
    private final VideoRepository videoRepository;
    private final CarritoItemsRepository carritoItemsRepository;
    private final HistorialRepository historialRepository;
    private final UserRepository userRepository;

    @Autowired
    public ColeccionController(ColeccionRepository coleccionRepository, VideoColeccionRepository videoColeccionRepository, VideoRepository videoRepository, CarritoItemsRepository carritoItemsRepository, HistorialRepository historialRepository, UserRepository userRepository) {
        this.coleccionRepository = coleccionRepository;
        this.videoColeccionRepository = videoColeccionRepository;
        this.videoRepository = videoRepository;
        this.carritoItemsRepository = carritoItemsRepository;
        this.historialRepository = historialRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/crearColeccion")
    public String crearColeccion() {
        return "plantillas/crearColeccion";
    }

    @PostMapping("/crearColeccion")
    public String crearColeccion(@RequestParam("titulo") String titulo, Model model) {
        Coleccion coleccion = new Coleccion();
        coleccion.setTitulo(titulo);
        coleccionRepository.save(coleccion);
        model.addAttribute("tareaFinalizada",
                "Colección creada correctamente. ISAN de la colección:" + coleccion.getIsan());
        return "plantillas/crearColeccion";
    }

    @GetMapping("/agregarVideoAColeccion")
    public String agregarVideo() {
        return "plantillas/agregarVideoColeccion";
    }

    @PostMapping("/agregarVideo")
    public String ColeccionYVideo(@RequestParam("coleccionIsan") String coleccionIsan,
            @RequestParam("videoIsan") String videoIsan, Model model) {

        try {
            int IsanColeccion = Integer.parseInt(coleccionIsan);
            int IsanVideo = Integer.parseInt(videoIsan);
            Coleccion coleccion = coleccionRepository.findByIsan(IsanColeccion);
            Video video = videoRepository.findByIsan(IsanVideo);

            if (video != null) {
                if (coleccion != null) {
                    VideoColeccion subir = new VideoColeccion();
                    subir.setIsanColeccion(coleccion.getIsan());
                    subir.setIsanVideo(video.getIsan());
                    videoColeccionRepository.save(subir);

                    model.addAttribute("tareaFinalizada", "Video agregado a la colección de forma correcta");
                } else {
                    model.addAttribute("errorColeccion", "No existe una colección con ese ISAN");
                }
            } else {
                model.addAttribute("errorVideo", "No existe un video con ese ISAN");
            }

        } catch (NumberFormatException e) {
            model.addAttribute("errorNumero", "ingresa un número, no una letra");
        }

        return "plantillas/agregarVideoColeccion";
    }

    @GetMapping("/Colecciones")
    public String verColecciones(Model model, Principal principal) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        List<VideoColeccion> coleccionesYVideos = videoColeccionRepository.findAllByOrderByIsanColeccion();

        Map<String, List<Map<String, Object>>> coleccionesMap = new LinkedHashMap<>();
        Integer lastIsanColeccion = null;
        String tituloColeccion = "Título desconocido";

        for (VideoColeccion item : coleccionesYVideos) {
            int isanColeccion = item.getIsanColeccion();
            int isanVideo = item.getIsanVideo();

            if (lastIsanColeccion == null || !lastIsanColeccion.equals(isanColeccion)) {
                Coleccion coleccion = coleccionRepository.findByIsan(isanColeccion);
                tituloColeccion = coleccion != null ? coleccion.getTitulo() : "Título desconocido";
                lastIsanColeccion = isanColeccion;
            }

            Video video = videoRepository.findByIsan(isanVideo);
            String tituloVideo = video != null ? video.getTitulo() : "Título de video desconocido";

            boolean enCarrito = carritoItemsRepository.existsByIdUsuarioAndIsan(usuario.getId(), isanVideo);
            boolean comprado = historialRepository.existsByIdUsuarioAndIsan(usuario.getId(), isanVideo);

            Map<String, Object> videoData = new HashMap<>();
            videoData.put("titulo", tituloVideo);
            videoData.put("isan", isanVideo);
            videoData.put("enCarrito", enCarrito);
            videoData.put("comprado", comprado);

            coleccionesMap.computeIfAbsent(tituloColeccion, k -> new ArrayList<>()).add(videoData);
        }

        model.addAttribute("coleccionesMap", coleccionesMap);
        return "plantillas/misColecciones";
    }

}
