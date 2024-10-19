package com.example.netpolix.Controller;

import com.example.netpolix.Repository.UserRepository;
import com.example.netpolix.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/Registrarse")
    public String registerUser(@RequestParam String email, @RequestParam String contraseña, @RequestParam String nombreUsuario, @RequestParam(required = false) Long referidoPor, Model model) {
        // Verificar si el usuario ya existe
        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("errorMessage", "El correo ya está registrado.");
            return "plantillas/Registrarse";
        }

        // Crear un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setContraseña(passwordEncoder.encode(contraseña));
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setRol("USER");
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setSaldo(0.0);
        usuario.setPuntos(0);
        usuario.setReferidoPor(referidoPor);

        // Guardar el usuario en la base de datos
        userRepository.save(usuario);

        // Agregar saldo al usuario que refirió
        if (referidoPor != null) {
            Usuario usuarioReferente = userRepository.findById(referidoPor).orElse(null);
            if (usuarioReferente != null) {
                usuarioReferente.setSaldo(usuarioReferente.getSaldo() + 5000);
                usuarioReferente.setPuntos(usuarioReferente.getPuntos() + 100);
                userRepository.save(usuarioReferente);
            }
        }

        // Redirigir a la página de inicio de sesión
        return "redirect:/InicioSesion";
    }

    @GetMapping("/InicioSesion")
    public String showLoginForm(Model model) {
        return "plantillas/InicioSesion";
    }
}