package View;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


public class Audio {
    private MediaPlayer[] mediaPlayers = new MediaPlayer[30];

    public Audio(){
        String _path1 = getClass().getResource("/Audio/TomAndJerry.wav").getPath();
        String _path2 = getClass().getResource("/Audio/GameMusic.wav").getPath();
        String _path3 = getClass().getResource("/Audio/Victory.wav").getPath();

        Media _media1 = new Media(new File(_path1).toURI().toString());
        Media _media2 = new Media(new File(_path2).toURI().toString());
        Media _media3 = new Media(new File(_path3).toURI().toString());

        mediaPlayers[0] = new MediaPlayer(_media1);
        mediaPlayers[1] = new MediaPlayer(_media2);
        mediaPlayers[2] = new MediaPlayer(_media3);
    }

    public void playOnce(int i){
        this.mediaPlayers[i].play();
    }

    public void playInLoop(int i){
        mediaPlayers[i].setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayers[i].play();
    }

    public void stop(int i){
        mediaPlayers[i].stop();
    }
}
