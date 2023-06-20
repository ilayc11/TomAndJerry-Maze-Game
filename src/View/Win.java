package View;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.*;
import java.util.Observable;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Win {
    public Label mazeRows;
    public Label mazeCols;
    public Label playerSteps;
    public Label optimalSteps;
    public Label gamePoints;
    public Label help;
    public ImageView trophyImage;
    public MyViewController myView;
    public Button playAgain;
    public static final Logger logger = LogManager.getLogger(Win.class);



    public void setWinWindow(String rows,String cols,String pSteps,String oSteps, String gameP,Boolean helpFlag,MyViewController mv){

        mazeRows.setText(rows);
        mazeCols.setText(cols);
        playerSteps.setText(pSteps);
        optimalSteps.setText(oSteps);
        gamePoints.setText(gameP);
        String medalType = "Didnt earned any medal :( ";
        if(helpFlag) help.setText("Yes");
        else help.setText("No");
        FileInputStream inputstream = null;
        int points = Integer.parseInt(gameP);
        try{
            if( points >= 80 ) {
                inputstream = new FileInputStream("Resources/Images/gold.png");
                medalType = "Gold medal";
            }
            else if( points >= 50) {
                inputstream = new FileInputStream("Resources/Images/silver.png");
                medalType = "Silver medal";
            }
            else {
                inputstream = new FileInputStream("Resources/Images/bronze.png");
                medalType = "Bronze medal";
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // write more information to the log:
        logger.info("Information about users game execution : ");
        logger.info("User's total points in the current game -->  " + points);
        logger.info("User's medal type -->  " + medalType);
        trophyImage.setImage(new Image(inputstream));
    }

    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) trophyImage.getScene().getWindow();
        stage.close();
    }
}
