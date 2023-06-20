package View;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


public class FXMLDocumentController implements Initializable {

    @FXML
    private MediaView StartingTitle;
    @FXML
    private MediaView StartingVideo;
    private File file;
    private Media media;
    private MediaPlayer mediaPlayer;
    
    @FXML
    private Label progress;
    public static Label label;
    
    
    @FXML
    private ProgressBar progressBar;
    
    public static ProgressBar statProgressBar;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       label = progress ;
       statProgressBar = progressBar;

        String _path2 = getClass().getResource("/Video/StartingTitle.MPEG").getPath();
        file = new File(_path2);
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        StartingTitle.setMediaPlayer(this.mediaPlayer);
    }
}