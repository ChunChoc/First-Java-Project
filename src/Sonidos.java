import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sonidos {
    Clip menu, juego, comer, esc, select, muerte;

    public void reproducirMenu(){
        try{
            menu = AudioSystem.getClip();
            menu.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\ChunChoc\\Documents\\ProyectoFinal\\Culebrita\\res\\Menu.wav")));
            menu.loop(1);
        }
        catch(Exception e){

        }
    }
    public void detenerMenu(){
        menu.stop();
    }

    public void reproducirJuego(){
        try{
            juego = AudioSystem.getClip();
            juego.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\ChunChoc\\Documents\\ProyectoFinal\\Culebrita\\res\\Juego.wav")));
            juego.loop(10);
        }
        catch(Exception e){

        }
    }
    public void detenerJuego(){
        juego.stop();
    }

    public void reproducirComer(){
        try{
            comer = AudioSystem.getClip();
            comer.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\ChunChoc\\Documents\\ProyectoFinal\\Culebrita\\res\\Comer.wav")));
            comer.start();
        }
        catch(Exception e){

        }
    }

    public void reproducirEsc(){
        try{
            esc = AudioSystem.getClip();
            esc.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\ChunChoc\\Documents\\ProyectoFinal\\Culebrita\\res\\Esc.wav")));
            esc.start();
        }
        catch(Exception e){

        }
    }

    public void reproducirSelect(){
        try{
            select = AudioSystem.getClip();
            select.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\ChunChoc\\Documents\\ProyectoFinal\\Culebrita\\res\\Selección.wav")));
            select.start();
        }
        catch(Exception e){

        }
    }

    public void reproducirMuerte(){
        try{
            muerte = AudioSystem.getClip();
            muerte.open(AudioSystem.getAudioInputStream(new File("C:\\Users\\ChunChoc\\Documents\\ProyectoFinal\\Culebrita\\res\\Muerte.wav")));
            muerte.start();
        }
        catch(Exception e){

        }
    }
    public void detenerMuerte(){
        muerte.stop();
    }
}