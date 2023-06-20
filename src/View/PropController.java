package View;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.util.*;

public class PropController {
    @FXML
    public Label key1;
    public Label value1;
    public Label key2;
    public Label value2;
    public Label key3;
    public Label value3;

    public Properties prop;

    @FXML
    public void setMatrix() {
        try {
            InputStream input = new FileInputStream("Resources/config.properties");
            prop = new Properties();
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        key1.setText("threadPoolSize");
        value1.setText(prop.getProperty("threadPoolSize"));

        key2.setText("mazeGeneratingAlgorithm");
        value2.setText(prop.getProperty("mazeGeneratingAlgorithm"));

        key3.setText("mazeSearchingAlgorithm");
        value3.setText(prop.getProperty("mazeSearchingAlgorithm"));
    }
}



