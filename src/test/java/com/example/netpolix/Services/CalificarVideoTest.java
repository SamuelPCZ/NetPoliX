package com.example.netpolix.Services;

import com.example.netpolix.Repository.CalificacionesRepository;
import com.example.netpolix.Repository.UserRepository;
import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.model.Calificaciones;
import com.example.netpolix.model.Usuario;
import com.example.netpolix.model.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CalificarVideoTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private CalificacionesRepository calificacionesRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private CalificarVideo calificarVideo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalificarVideo() {
        Video video = new Video();
        when(videoRepository.findByIsan(123)).thenReturn(video);
        when(calificacionesRepository.findByIsanAndIdUsuario(123, 1)).thenReturn(Collections.emptyList());

        Usuario usuario = new Usuario();
        usuario.setPuntos(100); // Initialize puntos to avoid NullPointerException
        when(userRepository.findById(1L)).thenReturn(Optional.of(usuario));

        calificarVideo.calificarVideo(123, 1, 5);

        verify(calificacionesRepository, times(1)).save(any(Calificaciones.class));
    }

    @Test
    public void testCalificarVideoNotFound() {
        when(videoRepository.findByIsan(123)).thenReturn(null);

        calificarVideo.calificarVideo(123, 1, 5);

        verify(calificacionesRepository, never()).save(any(Calificaciones.class));
    }

    @Test
    public void testCalificarVideoAlreadyRated() {
        Video video = new Video();
        Calificaciones calificacionExistente = new Calificaciones();
        when(videoRepository.findByIsan(123)).thenReturn(video);
        when(calificacionesRepository.findByIsanAndIdUsuario(123, 1)).thenReturn(Collections.singletonList(calificacionExistente));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calificarVideo.calificarVideo(123, 1, 5);
        });

        assertEquals("El usuario ya ha calificado este video.", exception.getMessage());
        verify(calificacionesRepository, never()).save(any(Calificaciones.class));
    }
}
