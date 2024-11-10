package com.example.netpolix.Services;

import com.example.netpolix.Repository.PromocionRepository;
import com.example.netpolix.model.Promocion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PromocionService {

    @Autowired
    private PromocionRepository promocionRepository;

    @Autowired
    private NotificacionesService notificacionesService;

    public List<Promocion> getActivePromotions() {
        LocalDateTime now = LocalDateTime.now();
        return promocionRepository.findByFechaInicioBeforeAndFechaFinAfter(now, now);
    }

    public Promocion createPromotion(String descripcion, Float descuento, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Promocion promocion = new Promocion();
        promocion.setDescripcion(descripcion);
        promocion.setDescuento(descuento);
        promocion.setFechaInicio(fechaInicio);
        promocion.setFechaFin(fechaFin);
        Promocion savedPromocion = promocionRepository.save(promocion);

        // Send notification to all users
        notificacionesService.sendNotificationToAllUsers("Nueva promoción: " + descripcion, "promocion");

        return savedPromocion;
    }

    public void deletePromotion(Long idPromocion) {
        Promocion promocion = promocionRepository.findById(idPromocion).orElse(null);
        if (promocion != null) {
            promocionRepository.delete(promocion);
            // Remove notification
            notificacionesService.removeNotificationByMessage("Nueva promoción: " + promocion.getDescripcion());
        }
    }

    public boolean hasActivePromotions() {
        return !getActivePromotions().isEmpty();
    }
}
