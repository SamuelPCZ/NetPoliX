package com.example.netpolix.Controller;

import com.example.netpolix.Repository.HistorialRepository;
import com.example.netpolix.Repository.InventarioRepository;
import com.example.netpolix.model.Historial;
import com.example.netpolix.model.Inventario;
import com.example.netpolix.model.Usuario;
import com.example.netpolix.Repository.UserRepository;
import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.model.Video;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class UsuarioController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private HistorialRepository historialRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Transactional
    @GetMapping("/usuarioPrincipal")
    public String showUsuarioPrincipal(Model model, Principal principal) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        model.addAttribute("usuarioId", usuario.getId());
        model.addAttribute("usuarioNombre", usuario.getNombreUsuario());
        log.info("Accessing /usuarioPrincipal");
        return "plantillas/UsuarioPrincipal";
    }

    @Transactional
    @GetMapping("/referidosPuntos")
    public String showReferidosPuntos(Model model, Principal principal) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        model.addAttribute("usuarioId", usuario.getId());
        model.addAttribute("usuarioPuntos", usuario.getPuntos());
        return "plantillas/ReferidosPuntos";
    }

    @Transactional
    @GetMapping("/consultarSaldo")
    public String showConsultarSaldo(Model model, Principal principal) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        model.addAttribute("usuario", usuario);
        return "plantillas/ConsultarSaldo";
    }

    @Transactional
    @GetMapping("/ingresarSaldo")
    public String showIngresarSaldo(Model model, Principal principal) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        model.addAttribute("usuario", usuario);
        return "plantillas/IngresarSaldo";
    }

    @PostMapping("/ingresarSaldo")
    @Transactional
    public String ingresarSaldo(@RequestParam Double monto, Principal principal, Model model) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        usuario.setSaldo(usuario.getSaldo() + monto);
        userRepository.save(usuario);
        model.addAttribute("usuario", usuario);
        return "redirect:/consultarSaldo";
    }

    @GetMapping("/historialCompras")
    @Transactional
    public String showHistorialCompras(Model model, Principal principal) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        List<Historial> historialCompras = historialRepository.findByIdUsuario(usuario.getId());

        List<HistorialDTO> historialDTOs = historialCompras.stream().map(historial -> {
            Video video = videoRepository.findById(historial.getIsan()).orElse(null);
            String nombreVideo = video != null ? video.getTitulo() : "Unknown";
            Double precio = video != null ? video.getPrecio() : 0.0;
            int isan = video.getIsan();
            return new HistorialDTO(historial, nombreVideo, precio, isan);
        }).collect(Collectors.toList());

        model.addAttribute("historialCompras", historialDTOs);
        return "plantillas/HistorialCompras";
    }

    @Transactional
    @GetMapping("/inventarioVideos")
    public String showInventarioVideos(Model model, Principal principal) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        List<Inventario> inventarioVideos = inventarioRepository.findByIdUsuario(usuario.getId());

        List<InventarioDTO> inventarioDTOs = inventarioVideos.stream().map(inventario -> {
            Video video = videoRepository.findById(inventario.getIsan()).orElse(null);
            String nombreVideo = video != null ? video.getTitulo() : "Unknown";
            return new InventarioDTO(inventario, nombreVideo);
        }).collect(Collectors.toList());

        model.addAttribute("inventarioVideos", inventarioDTOs);
        return "plantillas/InventarioVideos";
    }

    @GetMapping("/politicasAlquiler")
    public String PoliticasAlquiler() {
        return "plantillas/politicasAlquiler";
    }
    

    // DTO class to hold Historial, video name, and price
    public static class HistorialDTO {
        private Historial historial;
        private String nombreVideo;
        private Double precio;
        private int isan;

        public HistorialDTO(Historial historial, String nombreVideo, Double precio, int isan) {
            this.historial = historial;
            this.nombreVideo = nombreVideo;
            this.precio = precio;
            this.isan = isan;
        }

        public Historial getHistorial() {
            return historial;
        }

        public String getNombreVideo() {
            return nombreVideo;
        }

        public Double getPrecio() {
            return precio;
        }

        public int getIsan(){
            return isan;
        }
    }

    public static class InventarioDTO {
        private Inventario inventario;
        private String nombreVideo;

        public InventarioDTO(Inventario inventario, String nombreVideo) {
            this.inventario = inventario;
            this.nombreVideo = nombreVideo;
        }

        public Inventario getInventario() {
            return inventario;
        }

        public String getNombreVideo() {
            return nombreVideo;
        }
    }

}
