package com.example.netpolix.Controller;

import com.example.netpolix.Services.NotificacionesService;
import com.example.netpolix.Services.PromocionService;
import com.example.netpolix.model.Promocion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    @Autowired
    private NotificacionesService notificacionesService;

    @PostMapping("/crearPromocion")
    @Transactional
    public String crearPromocion(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("descuento") Float descuento,
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin,
            Model model) {

        if (promocionService.hasActivePromotions()) {
            model.addAttribute("errorMessage", "Ya hay promociones activas. No se pueden crear más promociones.");
            return "plantillas/promociones";
        }

        LocalDateTime fechaInicioParsed = LocalDateTime.parse(fechaInicio);
        LocalDateTime fechaFinParsed = LocalDateTime.parse(fechaFin);

        promocionService.createPromotion(descripcion, descuento, fechaInicioParsed, fechaFinParsed);

        return "redirect:/promociones";
    }

    @PostMapping("/eliminarPromocion")
    @Transactional
    public String eliminarPromocion(@RequestParam("idPromocion") Long idPromocion) {
        promocionService.deletePromotion(idPromocion);
        return "redirect:/promociones";
    }

    @GetMapping("/promociones")
    @Transactional
    public String verPromociones(Model model) {
        model.addAttribute("promociones", promocionService.getActivePromotions());
        return "plantillas/promociones";
    }
}