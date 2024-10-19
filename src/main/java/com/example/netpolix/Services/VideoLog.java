package com.example.netpolix.Services;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.example.netpolix.model.Video;
@Service
public class VideoLog {
    //Metodo para validar que el título del video no tenga caracteres especiales innecesarios.
    boolean ValidarTitulo(String Titulo) {
        String invalidCharacters = "/|=}{*&%--";
        for (char c : Titulo.toCharArray()) {
            if (invalidCharacters.indexOf(c) != -1) {
                return false;
            }
        }
        return true;
    }

    //La fecha de producción no puede ser hoy
    boolean ValidarAñoProduccion(LocalDate año) {
        return año.isBefore(LocalDate.now());
    }

    //la duración del video no puede ser 0 ni mayor a cuatro horas, ademas, un try catch por si el usuario
    //ingresa un valor muy alto para evitar errores de procesamiento
    boolean ValidarDuracionVideo(Duration duracion) {
        try {
            if(!duracion.isZero() && duracion.toMinutes() < 240) { //Que no sea 0 y no dure más de 4 horas
                return true;
            } else {
                return false;
            }
        }catch(IllegalArgumentException e) {
            return false;
        }
    }
    //pendiente por que debo conectar la base de datos, verificar que no se repitan debido a que el ISAN es unico
    void ISANUnico(long ISAN) {

    }
    boolean CategoriasValidas(String [] categoriasVideo) {
        try {
            return categoriasVideo.length==0? false: true;
        }catch(NullPointerException e) {
            return false;
        }

    }
    //Metodo para convertir Imagen a bytes, luego guardarla en la base de datos
    public byte[] convertirImagenABytes(String rutaImagen) throws FileNotFoundException {
        File file = new File(rutaImagen);
        FileInputStream fis = new FileInputStream(file);
        byte[] bytesArray = new byte[(int) file.length()];
        try {
            fis.read(bytesArray);
            fis.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytesArray;
    }
    public boolean PersonasInvolucradas(ArrayList<String> personas){
        return (personas.size() <=0)? false: true; //Tiene que haber minimo una persona tanto como director,
        //actor o productor
    }
    //Metodo para confirmar que una serie existe, sino, no se podra agregar a esta el video, necesaria la base
    //de datos
	/*public boolean ConfirmarSerie(){
	}*/
    public void AgregarVideoASerie(Video video){
    }
    //Metodo que nos dirá cuantas temporadas tiene una serie, para seleccionar la temporada y agregar el video
    void TemporadasSerie(){
    }
    //primero verifica el numero del ultimo capitulo agregado y le sumara 20 para seleccionar el # del capitulo
    //si el administrador lo desea, puede poner manualmente el numero del capitulo
    void NumeroCapitulo(){
    }
}
