package com.example.netpolix.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.netpolix.Repository.VideoRepository;
import com.example.netpolix.model.Video;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    public Video buscarPorIsan(int isan) {
        return videoRepository.findByIsan(isan); 
    }
}

