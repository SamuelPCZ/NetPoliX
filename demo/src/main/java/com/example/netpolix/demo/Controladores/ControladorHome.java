package com.example.netpolix.demo.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ControladorHome {

    @GetMapping("/home")
    public String Home(){
        return "plantillas/home";
    }

    @GetMapping("/IniciarSesion")
    public String IniciarSesion() {
        return "plantillas/InicioSesion";
    }
    
    @GetMapping("/Registrarse")
    public String Registrarse(){
        return "plantillas/Registrarse";
    }
    

}
