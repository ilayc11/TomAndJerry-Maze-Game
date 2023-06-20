package View;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveLoadController {
    public TextField SaveInputName;
    public TextField LoadInputName;
    public String saveMazeName;
    public String loadMazeName;
    public Label ChooseNameLabel;
    public Button saveMaze;
    public Button loadBtn;
    public static final Logger logger = LogManager.getLogger(SaveLoadController.class);


    public void saveMaze(ActionEvent actionEvent) {
        saveMazeName = SaveInputName.getText();
        logger.info("Saved name the user choose -->  " + SaveInputName.getText());
        Stage stage = (Stage)SaveInputName.getScene().getWindow();
        stage.close();
    }
    public String getSavedName(){return saveMazeName;}

    public String getLoadName(){return loadMazeName;}

    public void loadMaze(ActionEvent actionEvent) {
        loadMazeName = LoadInputName.getText();
        Stage stage = (Stage) LoadInputName.getScene().getWindow();
        stage.close();
    }
}
