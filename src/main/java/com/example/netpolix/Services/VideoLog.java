package com.example.netpolix.Services;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.netpolix.Repository.SerieRepository;
import com.example.netpolix.Repository.TemporadaRepository;
import com.example.netpolix.Repository.VideoRepository;



@Service
public class VideoLog {

	@Autowired
	private TemporadaRepository tempRepository;

	@Autowired
    private SerieRepository serieRepository;

	@Autowired 
	private VideoRepository videoRepository;

    //Metodo para validar que el título del video no tenga caracteres especiales innecesarios.
    public boolean ValidarTitulo(String Titulo) {
		String invalidCharacters = "/|=}{*&%--";
		for (char c : Titulo.toCharArray()) {
			if (invalidCharacters.indexOf(c) != -1) {
				return false;
			}
		}
		return true;
	}
	
    //La fecha de producción no puede ser hoy
	public boolean ValidarAñoProduccion(LocalDate año) {
		if(año == null){
			return false;
		}
		return año.isBefore(LocalDate.now());
	}
	
    //la duración del video no puede ser 0 ni mayor a cuatro horas, ademas, un try catch por si el usuario
    //ingresa un valor muy alto para evitar errores de procesamiento
	public boolean ValidarDuracionVideo(String duracion) {
		try {
			long minutos = Long.parseLong(duracion); 
            Duration duration = Duration.ofMinutes(minutos);
			if(!duration.isZero() && duration.toMinutes() < 240) { //Que no sea 0 y no dure más de 4 horas
				return true;
			} else {
				return false;
			}		
		}catch(IllegalArgumentException e) {
			return false;
		}
	}

	public boolean PersonasInvolucradas(String persona){
		ArrayList<String> personas = new ArrayList<>();
		String[] personaArray = persona.split(",");
		Collections.addAll(personas, personaArray);

		return (personas.size() <=0)? false: true; //Tiene que haber minimo una persona tanto como director,
		//actor o productor
	}

    public boolean ConfirmarSerie(int serieId) {
        if (!serieRepository.existsById(serieId)) {
			return false;
        }
		return true;
	}

	public boolean ConfirmarTemporada(int idTemporada){
		return tempRepository.existsById(idTemporada);

	}


}
