package com.example.netpolix.Controller;

import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class AdminController {

    @GetMapping("/CrearSerie")
    public String CrearSerie() {
        return "plantillas/CrearSerie";
    }

    @GetMapping("/agregarTemporada")
    public String AgregarTemporada() {
        return "/plantillas/AgregarTemporada";
    }

    @GetMapping("/SubirVideo")
    public String PaginaVideo(Model model) {
        model.addAttribute("fechaRegistro", LocalDate.now());
        return "plantillas/SubirVideo";
    }

    @GetMapping("/Admin")
    public String getMethodName() {
        return "plantillas/Admin";
    }
    
    

}
