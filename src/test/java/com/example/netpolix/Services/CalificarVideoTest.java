package com.example.netpolix.Services;

import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.model.Video;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CalificarVideoTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private CalificarVideo calificarVideo;

    public CalificarVideoTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalificarVideo() {
        Video video = new Video();
        video.setCalificacion(4);
        when(videoRepository.findByIsan(123)).thenReturn(video);

        calificarVideo.calificarVideo(123, 1,  5);
        assertEquals(4.5, video.getCalificacion());
        verify(videoRepository, times(1)).save(video);
    }

    @Test
    public void testCalificarVideoNotFound() {
        when(videoRepository.findByIsan(123)).thenReturn(null);

        calificarVideo.calificarVideo(123, 1,  5);
        verify(videoRepository, never()).save(any(Video.class));
    }
}
