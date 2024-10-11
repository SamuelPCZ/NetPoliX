package com.example.netpolix.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class UsuarioController {

    @GetMapping("/usuarioPrincipal")
    public String showUsuarioPrincipal() {
        log.info("Accessing /usuarioPrincipal");
        return "UsuarioPrincipal";
    }
}
