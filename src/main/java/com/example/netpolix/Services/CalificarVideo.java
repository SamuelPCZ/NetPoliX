package com.example.netpolix.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpolix.Repository.CalificacionesRepository;
import com.example.netpolix.Repository.UserRepository;
import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.model.Calificaciones;
import com.example.netpolix.model.Usuario;
import com.example.netpolix.model.Video;

import java.util.List;

@Service
public class CalificarVideo {

    private final VideoRepository videoRepository;

    private final CalificacionesRepository calificacionesRepository;

    private final UserRepository userRepository;

    public CalificarVideo(VideoRepository videoRepository, CalificacionesRepository calificacionesRepository, UserRepository userRepository) {
        this.videoRepository = videoRepository;
        this.calificacionesRepository = calificacionesRepository;
        this.userRepository = userRepository;
    }

    public void calificarVideo(int isan, int idUsuario, float calificacion) {
        Video video = videoRepository.findByIsan(isan);
        if (video != null) {
            // Verificar si el usuario ya ha calificado el video
            List<Calificaciones> calificacionesExistentes = calificacionesRepository.findByIsanAndIdUsuario(isan, idUsuario);
            if (calificacionesExistentes.isEmpty()) {
                Calificaciones nuevaCalificacion = new Calificaciones();
                nuevaCalificacion.setIsan(isan);
                nuevaCalificacion.setIdUsuario(idUsuario);
                nuevaCalificacion.setCalificacion(calificacion);
                calificacionesRepository.save(nuevaCalificacion);

                // Actualizar los puntos del usuario
                Usuario usuario = userRepository.findById((long) idUsuario).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
                usuario.setPuntos(usuario.getPuntos() + 20);
                userRepository.save(usuario);
            } else {
                throw new IllegalArgumentException("El usuario ya ha calificado este video.");
            }
        }
    }

    public String obtenerCalificacionPromedio(int isan) {
        List<Calificaciones> calificaciones = calificacionesRepository.findByIsan(isan);
        if (calificaciones.isEmpty()) {
            return "El video no cuenta con calificaciones";
        } else {
            double promedio = calificaciones.stream()
                    .mapToDouble(Calificaciones::getCalificacion)
                    .average()
                    .orElse(0.0);
            return "Calificación promedio: " + promedio;
        }
    }
}