package com.example.netpolix.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.model.Video;

@Service
public class CalificarVideo {

    @Autowired
    private VideoRepository videoRepository;

    public void calificarVideo(int isan, int calificacion){
        Video video = videoRepository.findByIsan(isan);
        if(video != null){
            double nuevaCalificacion = (video.getCalificacion() + calificacion) / 2.0;
            video.setCalificacion(nuevaCalificacion);
            videoRepository.save(video);
        }
    }
}