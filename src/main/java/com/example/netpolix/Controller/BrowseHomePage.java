package com.example.netpolix.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BrowseHomePage {
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
