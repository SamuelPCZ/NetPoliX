package com.example.netpolix.Controller;


import com.example.netpolix.model.Usuario;
import com.example.netpolix.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Slf4j
@Controller
public class UsuarioController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/usuarioPrincipal")
    public String showUsuarioPrincipal(Model model, Principal principal) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        model.addAttribute("usuarioId", usuario.getId());
        model.addAttribute("usuarioNombre", usuario.getNombreUsuario());
        log.info("Accessing /usuarioPrincipal");
        return "plantillas/UsuarioPrincipal";
    }

    @GetMapping("/referidosPuntos")
    public String showReferidosPuntos(Model model, Principal principal) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        model.addAttribute("usuarioId", usuario.getId());
        model.addAttribute("usuarioPuntos", usuario.getPuntos());
        return "plantillas/ReferidosPuntos";
    }

    @GetMapping("/consultarSaldo")
    public String showConsultarSaldo(Model model, Principal principal) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        model.addAttribute("usuario", usuario);
        return "plantillas/ConsultarSaldo";
    }

    @GetMapping("/ingresarSaldo")
    public String showIngresarSaldo(Model model, Principal principal) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        model.addAttribute("usuario", usuario);
        return "plantillas/IngresarSaldo";
    }

    @PostMapping("/ingresarSaldo")
    public String ingresarSaldo(@RequestParam Double monto, Principal principal, Model model) {
        Usuario usuario = userRepository.findByEmail(principal.getName());
        usuario.setSaldo(usuario.getSaldo() + monto);
        userRepository.save(usuario);
        model.addAttribute("usuario", usuario);
        return "redirect:/consultarSaldo";
    }
}
